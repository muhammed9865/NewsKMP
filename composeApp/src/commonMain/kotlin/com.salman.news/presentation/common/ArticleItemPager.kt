package com.salman.news.presentation.common

import com.salman.news.core.Resource
import com.salman.news.domain.model.Article
import com.salman.news.domain.repository.ArticleRepository
import com.salman.news.paging.impl.DefaultPager
import com.salman.news.presentation.model.ArticlePage
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update

/**
 * Created by Muhammed Salman email(mahmadslman@gmail.com) on 2/2/2024.
 */
class ArticleItemPager(
    private val repository: ArticleRepository
) {

    private var countryCode: String? = null
    private val pager = DefaultPager(
        initialKey = 1,
        onLoadingUpdated = { isLoading ->
            _pagingState.update { if (isLoading) Resource.Loading else Resource.Idle }
        },
        onRequest = { page -> loadPage(page) },
        onSuccess = { items, nextPage, isInitialPage ->
            _pagingState.update {
                val articlesPage = ArticlePage(items, isInitialPage, nextPage.dec())
                Resource.Success(articlesPage)
            }
        },
        onError = { throwable -> _pagingState.update { Resource.Error(throwable) } },
        getNextKey = { page -> page + 1 }
    )

    private val _pagingState = MutableStateFlow<Resource<ArticlePage>>(Resource.Idle)
    val pagingState = _pagingState.asStateFlow()

    suspend fun loadNextPage(countryCode: String) {
        this.countryCode = countryCode
        pager.loadNextItems()
    }

    suspend fun reset() {
        pager.reset()
    }

    fun cancel() {
        pager.cancel()
    }

    private suspend fun loadPage(page: Int): Result<List<Article>> {
        return if (countryCode == null)
            Result.failure(IllegalArgumentException())
        else {
            val loadingResult = repository.loadArticles(page, countryCode!!)
            if (loadingResult.isSuccess) {
                Result.success(emptyList())
            } else {
                Result.failure(loadingResult.exceptionOrNull()!!)
            }
        }
    }

}