package com.salman.news.presentation.screen.search

import com.salman.news.core.CoroutineViewModel
import com.salman.news.core.Resource
import com.salman.news.domain.model.SearchTimeFrame
import com.salman.news.domain.model.SuggestionsGroup
import com.salman.news.domain.usecases.SearchByQueryUseCase
import com.salman.news.logger.Logger
import kotlinx.coroutines.FlowPreview
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.debounce
import kotlinx.coroutines.flow.filter
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

/**
 * Created by Muhammed Salman email(mahmadslman@gmail.com) on 2/24/2024.
 */
class SearchViewModel(
    private val searchByQueryUseCase: SearchByQueryUseCase
) : CoroutineViewModel() {

    companion object {
        private const val SEARCH_DEBOUNCE_TIME = 400L
    }

    private val mutableState = MutableStateFlow(SearchState())
    val state = mutableState.asStateFlow()
    private var searchQueryFlow = MutableStateFlow("")
    private var searchingJob: Job? = null

    init {
        initSearchingInBackground()
    }

    fun onQueryChanged(query: String) {
        mutableState.update { it.copy(query = query) }
        searchQueryFlow.update { query }
    }

    fun onTimeFrameSelected(timeFrame: SearchTimeFrame) {
        mutableState.update { it.copy(selectedTimeFrame = timeFrame) }
    }

    @OptIn(FlowPreview::class)
    private fun initSearchingInBackground() {
        scope.launch {
            searchQueryFlow
                .debounce(SEARCH_DEBOUNCE_TIME)
                .filter { it.isNotBlank() }
                .collect(::search)
        }
    }

    private fun search(query: String) {
        searchingJob?.cancel()
        searchingJob = scope.launch {
            searchByQueryUseCase(
                query,
                state.value.selectedTimeFrame
            ).collect(::handleSearchResult)
        }
    }

    private fun handleSearchResult(searchResult: Resource<List<SuggestionsGroup>>) {
        when (searchResult) {
            is Resource.Loading -> {
                mutableState.update { it.copy(isLoading = true) }
            }

            is Resource.Success -> {
                mutableState.update {
                    it.copy(isLoading = false, suggestions = searchResult.data)
                }
            }

            is Resource.Error -> {
                Logger.error(this, "handleSearchResult: ${searchResult.throwable}")
                mutableState.update {
                    it.copy(isLoading = false, error = searchResult.throwable)
                }
            }

            else -> {}
        }
    }
}