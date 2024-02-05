package com.salman.news.presentation.screen.bookmark

import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.snapshotFlow
import com.salman.news.core.CoroutineViewModel
import com.salman.news.core.addOrRemove
import com.salman.news.domain.repository.ArticleRepository
import com.salman.news.domain.usecases.GetBookmarkedArticlesFlowUseCase
import com.salman.news.domain.usecases.ToggleArticleBookmarkUseCase
import com.salman.news.logger.Logger
import com.salman.news.presentation.model.ArticleUI
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.isActive
import kotlinx.coroutines.launch

/**
 * Created by Muhammed Salman email(mahmadslman@gmail.com) on 2/5/2024.
 */
class BookmarkViewModel(
    private val articleRepository: ArticleRepository,
    private val getBookmarkedArticles: GetBookmarkedArticlesFlowUseCase,
    private val toggleArticleBookmarkUseCase: ToggleArticleBookmarkUseCase,
) : CoroutineViewModel() {

    private var articleIDsWithMenuOpen = hashSetOf<Long>()

    // States
    private var _state = MutableStateFlow(BookmarkState())
    val state = _state.asStateFlow()
    private val _articles = mutableStateListOf<ArticleUI>()
    val articles
        get() = snapshotFlow { _articles.toList() }

    init {
        loadBookmarkedArticles()
    }

    private fun loadBookmarkedArticles() {
        scope.launch {
            getBookmarkedArticles(articleIDsWithMenuOpen)
                .collect {
                    Logger.debug(this@BookmarkViewModel, it.toString())
                    _articles.clear()
                    _articles.addAll(it)
                    setState(isLoading = false, noArticlesAvailable = it.isEmpty())
                }
        }
    }

    fun toggleBookmark(article: ArticleUI) {
        scope.launch {
            toggleArticleBookmarkUseCase(article)
        }
    }

    fun toggleArticleOptionsMenu(index: Int) {
        val article = _articles[index]
        val isOpen = article.isOptionsMenuOpen
        _articles[index] = article.copy(isOptionsMenuOpen = !isOpen)
        articleIDsWithMenuOpen.addOrRemove(article.article.id)

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

    private fun setState(
        isLoading: Boolean = state.value.isLoadingArticles,
        noArticlesAvailable: Boolean = state.value.noArticlesAvailable
    ) {
        _state.update {
            it.copy(isLoadingArticles = isLoading, noArticlesAvailable = noArticlesAvailable)
        }
    }

}