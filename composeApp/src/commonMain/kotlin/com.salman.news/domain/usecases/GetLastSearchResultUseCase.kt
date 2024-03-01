package com.salman.news.domain.usecases

import com.salman.news.domain.model.Article
import com.salman.news.domain.repository.ArticleRepository
import com.salman.news.domain.repository.SearchRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

/**
 * Created by Muhammed Salman email(mahmadslman@gmail.com) on 2/24/2024.
 */
class GetLastSearchResultUseCase(
    private val searchRepository: SearchRepository,
    private val articleRepository: ArticleRepository,
) {
    suspend operator fun invoke(): Flow<List<Article>> {
        val result = searchRepository.getLastSearchResult()
        return flow {
            articleRepository.getBookmarkedArticlesFlow()
                .collect {
                    val bookmarkedArticleIds = it.map { it.id }
                    val lastSearchResult = result.map { article ->
                        article.copy(isSaved = bookmarkedArticleIds.contains(article.id))
                    }
                    emit(lastSearchResult)
                }
        }
    }

}