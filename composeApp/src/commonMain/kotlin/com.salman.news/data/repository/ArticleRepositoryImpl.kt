package com.salman.news.data.repository

import com.salman.news.data.mapper.toDomain
import com.salman.news.data.source.local.ArticlesLocalDataSource
import com.salman.news.data.source.remote.ArticlesRemoteDataSource
import com.salman.news.data.source.remote.model.article.Source
import com.salman.news.domain.model.Article
import com.salman.news.domain.model.ArticleSource
import com.salman.news.domain.repository.ArticleRepository
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.launch

/**
 * Created by Muhammed Salman email(mahmadslman@gmail.com) on 1/26/2024.
 */
class ArticleRepositoryImpl(
    private val remoteDataSource: ArticlesRemoteDataSource,
    private val localDataSource: ArticlesLocalDataSource,
): ArticleRepository {

    override suspend fun loadArticles(page: Int, countryCode: String): Result<Unit> {
        val articlesDTO = remoteDataSource.getTopHeadlines(page, countryCode)
        if (articlesDTO.isFailure) {
            return Result.failure(articlesDTO.exceptionOrNull()!!)
        }

        val articles = articlesDTO.getOrThrow().articles
        val sources = articles.map { it.source }

        coroutineScope {
            launch {
                localDataSource.insertArticles(articles)
            }
            launch {
                localDataSource.insertSources(sources)
            }
        }

        return Result.success(Unit)
    }

    override suspend fun getArticlesFlow(): Flow<List<Article>> {
        return localDataSource.getArticlesFlow().map { entities ->
            entities.map {
                it.toDomain()
            }
        }
    }

    override suspend fun toggleArticleBookmark(id: Long) {
        val articleEntity = localDataSource.getArticleById(id) ?: return
        val isSaved = articleEntity.isSaved
        val updatedArticle = articleEntity.copy(isSaved = !isSaved)

        localDataSource.updateArticle(updatedArticle)
    }

    override suspend fun muteSource(source: ArticleSource) {
        localDataSource.muteSource(Source(source.id, source.name))
    }

    override suspend fun muteAuthor(author: String) {
        localDataSource.muteAuthor(author)
    }
}