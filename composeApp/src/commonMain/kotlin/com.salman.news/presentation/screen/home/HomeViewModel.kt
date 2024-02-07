package com.salman.news.presentation.screen.home

import androidx.compose.runtime.mutableStateListOf
import com.salman.news.core.CoroutineViewModel
import com.salman.news.core.addOrRemove
import com.salman.news.domain.repository.ArticleRepository
import com.salman.news.domain.usecases.GetArticlesFlowUseCase
import com.salman.news.domain.usecases.ToggleArticleBookmarkUseCase
import com.salman.news.presentation.common.ArticleItemPager
import com.salman.news.presentation.model.ArticleUI
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

/**
 * Created by Muhammed Salman email(mahmadslman@gmail.com) on 1/25/2024.
 */
class HomeViewModel(
    private val articleRepository: ArticleRepository,
    private val getArticlesFlowUseCase: GetArticlesFlowUseCase,
    private val toggleArticleBookmarkUseCase: ToggleArticleBookmarkUseCase,
) : CoroutineViewModel() {

    private var articleIDsWithMenuOpen = hashSetOf<Long>()
    private var countryCode: String? = null
    private val pager = ArticleItemPager(articleRepository)

    // States
    private var _state = MutableStateFlow(HomeState())
    val state = _state.asStateFlow()
    private val currState
        get() = _state.value
    val articles by lazy {
        mutableStateListOf<ArticleUI>()
    }

    init {
        scope.launch {
            getArticlesFlowUseCase(articleIDsWithMenuOpen)
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

    fun toggleArticleBookmark(articleUI: ArticleUI) {
        scope.launchIO {
            toggleArticleBookmarkUseCase(articleUI)
        }
    }

    fun muteAuthor(article: ArticleUI) {
        scope.launchIO {
            articleRepository.muteAuthor(article.article.author)
        }
    }

    fun muteSource(article: ArticleUI) {
        scope.launchIO {
            articleRepository.muteSource(article.article.source)
        }
    }

    fun toggleArticleOptionsMenu(index: Int) {
        val article = articles[index]
        val isOpen = article.isOptionsMenuOpen
        articles[index] = article.copy(isOptionsMenuOpen = !isOpen)
        articleIDsWithMenuOpen.addOrRemove(article.article.id)
    }
}
