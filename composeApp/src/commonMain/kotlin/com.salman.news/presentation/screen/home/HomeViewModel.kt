package com.salman.news.presentation.screen.home

import com.salman.news.domain.repository.ArticleRepository
import com.salman.news.domain.usecases.BlockAuthorUseCase
import com.salman.news.domain.usecases.BlockSourceUseCase
import com.salman.news.domain.usecases.GetArticlesFlowUseCase
import com.salman.news.domain.usecases.ToggleArticleBookmarkUseCase
import com.salman.news.presentation.common.ArticleItemPager
import com.salman.news.presentation.common.ArticlesViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.launch

/**
 * Created by Muhammed Salman email(mahmadslman@gmail.com) on 1/25/2024.
 */
class HomeViewModel(
    articleRepository: ArticleRepository,
    blockAuthorUseCase: BlockAuthorUseCase,
    blockSourceUseCase: BlockSourceUseCase,
    private val getArticlesFlowUseCase: GetArticlesFlowUseCase,
    toggleArticleBookmarkUseCase: ToggleArticleBookmarkUseCase,
) : ArticlesViewModel(blockAuthorUseCase, blockSourceUseCase, toggleArticleBookmarkUseCase) {

    private var countryCode: String? = null
    private val pager = ArticleItemPager(articleRepository)

    // States
    private var _state = MutableStateFlow(HomeState())
    val state = _state.asStateFlow()
    private val currState
        get() = _state.value


    init {
        scope.launch {
            getArticlesFlowUseCase()
                .map {
                    it.map { article -> article.toUI(articleIDsWithMenuOpen) }
                }
                .collect {
                    articles.clear()
                    articles.addAll(it)
                }
        }
    }

    fun loadInitialArticles(countryCode: String) {
        this.countryCode = countryCode
        _state.value = currState.copy(isLoadingInitialArticles = true)
        scope.launchIO {
            pager.loadNextPage(countryCode)
            _state.value = currState.copy(
                isLoadingInitialArticles = false,
            )
        }
    }
}
