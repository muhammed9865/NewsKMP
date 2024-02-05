package com.salman.news.presentation.screen.home

import androidx.compose.runtime.mutableStateListOf
import com.salman.news.core.CoroutineViewModel
import com.salman.news.core.Resource
import com.salman.news.domain.model.Article
import com.salman.news.domain.repository.ArticleRepository
import com.salman.news.presentation.common.ArticleItemPager
import com.salman.news.presentation.model.ArticleUI
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

/**
 * Created by Muhammed Salman email(mahmadslman@gmail.com) on 1/25/2024.
 */
class HomeViewModel(
    private val articleRepository: ArticleRepository,
) : CoroutineViewModel() {

    private var articleIDsWithMenuOpen = mutableMapOf<Long, Unit>()
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
        scope.launchIO {
            articleRepository
                .getArticlesFlow()
                .map { articlesDTO ->
                    articlesDTO.map { article -> article.toUI() }
                }.collect {
                    launchMain {
                        articles.clear()
                        articles.addAll(it)
                    }
                }
        }

        scope.launch {
            pager.pagingState.collect { resource ->
                when (resource) {
                    is Resource.Loading -> {
                        _state.update { it.copy(isLoadingMoreArticles = true) }
                    }

                    is Resource.Success -> {
                        _state.update { it.copy(isLoadingMoreArticles = false) }
                    }

                    is Resource.Error -> {
                        _state.update { it.copy(failedToLoadMoreArticles = true) }
                    }
                    else -> {}
                }

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

    fun loadMoreArticles() {
        if (countryCode == null)
            return

        scope.launchIO {
            pager.loadNextPage(countryCode!!)
        }
    }

    fun toggleArticleBookmark(articleUI: ArticleUI) {
        scope.launchIO {
            articleRepository.toggleArticleBookmark(articleUI.article.id)
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
        articleIDsWithMenuOpen.addOrRemove(article.article.id, Unit)

    }

    private suspend fun loadArticles(page: Int, countryCode: String): Throwable? {
        return articleRepository.loadArticles(page, countryCode).exceptionOrNull()
    }

    private fun Article.toUI() = ArticleUI(this, articleIDsWithMenuOpen.containsKey(id))
    private fun <K, V> MutableMap<K, V>.addOrRemove(key: K, value: V) {
        if (containsKey(key)) {
            remove(key)
        } else {
            put(key, value)
        }
    }
}