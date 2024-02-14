package com.salman.news.presentation.screen.blocklist

import com.salman.news.core.CoroutineViewModel
import com.salman.news.core.Resource
import com.salman.news.domain.model.ArticleAuthor
import com.salman.news.domain.model.ArticleSource
import com.salman.news.domain.model.BlockListedItem
import com.salman.news.domain.usecases.BlockAuthorUseCase
import com.salman.news.domain.usecases.BlockSourceUseCase
import com.salman.news.domain.usecases.ClearBlackListUseCase
import com.salman.news.domain.usecases.GetAllAuthorsUseCase
import com.salman.news.domain.usecases.GetAllSourcesUseCase
import com.salman.news.domain.usecases.GetBlockedAuthorsFlowUseCase
import com.salman.news.domain.usecases.GetBlockedSourcesFlowUseCase
import com.salman.news.domain.usecases.RemoveItemFromBlockListUseCase
import com.salman.news.logger.Logger
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

/**
 * Created by Muhammed Salman email(mahmadslman@gmail.com) on 2/10/2024.
 */
class BlockListViewModel(
    private val blockAuthorUseCase: BlockAuthorUseCase,
    private val blockSourceUseCase: BlockSourceUseCase,
    private val getAllAuthorsUseCase: GetAllAuthorsUseCase,
    private val getAllSourcesUseCase: GetAllSourcesUseCase,
    private val getBlockedSourcesFlowUseCase: GetBlockedSourcesFlowUseCase,
    private val getBlockedAuthorsFlowUseCase: GetBlockedAuthorsFlowUseCase,
    private val removeItemFromBlackList: RemoveItemFromBlockListUseCase,
    private val clearBlackListUseCase: ClearBlackListUseCase
) : CoroutineViewModel() {

    private val mutableState = MutableStateFlow(BlockListState())
    val state = mutableState.asStateFlow()

    init {
        loadBlockList()
    }

    fun onQueryChanged(query: String, isAuthor: Boolean) {
        mutableState.update {
            if (isAuthor) {
                it.copy(authorSearchQuery = query)
            } else {
                it.copy(sourceSearchQuery = query)
            }
        }
    }

    fun toggleBlockNewItems() = scope.launchIO {
        mutableState.update {
            it.copy(showBlockNewItems = !it.showBlockNewItems)
        }
        if (state.value.showBlockNewItems) {
            loadAllSourcesAndAuthors()
        }
    }

    fun blockAuthor(author: ArticleAuthor) {
        scope.launchIO {
            blockAuthorUseCase(author.name)
        }
    }

    fun blockSource(source: ArticleSource) {
        scope.launchIO {
            blockSourceUseCase(source)
        }
    }

    fun clearBlackList() {
        scope.launchIO {
            clearBlackListUseCase()
        }
    }

    fun removeItem(item: BlockListedItem) {
        scope.launch {
            removeItemFromBlackList(item)
        }
    }

    private fun loadAllSourcesAndAuthors() {
        scope.launch {
            launch {
                getAllAuthorsUseCase()
                    .catch { e ->
                        Logger.error(this@BlockListViewModel, "", e)
                    }
                    .collect { authorsResource ->
                        mutableState.update {
                            it.copy(allAuthors = authorsResource)
                        }
                    }
            }

            launch {
                getAllSourcesUseCase()
                    .catch { e ->
                        Logger.error(this@BlockListViewModel,"", e)
                    }
                    .collect { sourcesResource ->
                        mutableState.update {
                            it.copy(allSources = sourcesResource)
                        }
                    }
            }
        }
    }

    private fun loadBlockList() {
        scope.launch {
            launch {
                mutableState.update {
                    it.copy(blockedSources = Resource.Loading)
                }
                getBlockedSourcesFlowUseCase()
                    .collect { blockedSources ->
                        Logger.debug(this@BlockListViewModel, "Sources: $blockedSources")
                        mutableState.update {
                            it.copy(blockedSources = blockedSources)
                        }
                    }
            }

            launch {
                getBlockedAuthorsFlowUseCase()
                    .collect { blockedAuthors ->
                        Logger.debug(this@BlockListViewModel, "Authors: $blockedAuthors")
                        mutableState.update {
                            it.copy(blockedAuthors = blockedAuthors)
                        }
                    }
            }
        }
    }
}