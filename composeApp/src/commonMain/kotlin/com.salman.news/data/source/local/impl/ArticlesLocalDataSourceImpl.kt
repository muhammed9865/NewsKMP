package com.salman.news.data.source.local.impl

import app.cash.sqldelight.coroutines.asFlow
import app.cash.sqldelight.coroutines.mapToList
import com.salman.news.data.source.local.ArticlesLocalDataSource
import com.salman.news.data.source.local.IDHashGenerator
import com.salman.news.data.source.local.entity.ArticleEntity
import com.salman.news.data.source.local.entity.SourceEntity
import com.salman.news.data.source.remote.model.article.Article
import com.salman.news.data.source.remote.model.article.Source
import com.salman.news.database.NewsDatabase
import comsalmannewsdata.query.GetArticles
import comsalmannewsdata.query.GetSavedArticles
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import kotlin.coroutines.coroutineContext

/**
 * Created by Muhammed Salman email(mahmadslman@gmail.com) on 1/26/2024.
 */
class ArticlesLocalDataSourceImpl(
    private val database: NewsDatabase
) : ArticlesLocalDataSource {

    private val articleQueries
        get() = database.articleQueriesQueries

    private val sourceQueries
        get() = database.sourceQueries

    override suspend fun getArticlesFlow(): Flow<List<ArticleEntity>> {
        return articleQueries.getArticles()
            .asFlow()
            .mapToList(coroutineContext)
            .map { list ->
                list.map { getArticles ->
                    getArticles.mapToArticleEntity()
                }
            }
    }

    override suspend fun getSavedArticlesFlow(): Flow<List<ArticleEntity>> {
        return articleQueries.getSavedArticles()
            .asFlow()
            .mapToList(coroutineContext)
            .map { list ->
                list.map { getSavedArticles ->
                    getSavedArticles.mapToArticleEntity()
                }
            }
    }

    override suspend fun getArticleById(id: Int): ArticleEntity? {
        val result = articleQueries.getArticleById(id.toLong())
            .executeAsOneOrNull() ?: return null

        return with(result) {
            val source = SourceEntity(source_id, source_name)
            ArticleEntity(
                id = this.id,
                title = title,
                description = description,
                content = content,
                articleUrl = article_url,
                imageUrl = image_url,
                source = source,
                publishedDate = publish_date,
                author = author,
                isSaved = is_saved.toBoolean()
            )
        }
    }

    override suspend fun updateArticle(article: ArticleEntity) {
        articleQueries.insertOrReplaceArticle(article.toArticle())
    }

    override suspend fun insertArticles(articles: List<Article>) {
        articleQueries.transaction {
            articles.forEach { article ->
                articleQueries.insertOrReplaceArticle(
                    with(article) {
                        val id = IDHashGenerator.generate(title, article.source.name, url, author ?: "")
                        comsalmannewsdata.table.Article(
                            id = id,
                            title = title,
                            description = description,
                            content = content,
                            publish_date = publishedAt,
                            author = author,
                            is_saved = 0,
                            article_url = url,
                            image_url = urlToImage,
                            source_name = article.source.name
                        )
                    }
                )
            }
        }
    }

    override suspend fun insertSources(sources: List<Source>) {
        database.transaction {
            sources.forEach {
                sourceQueries.insertSource(it.name, it.id)
            }
        }
    }

    override suspend fun muteSource(source: Source) {
        articleQueries.muteSource(source.name, null)
    }

    override suspend fun muteAuthor(author: String) {
        articleQueries.muteSource(null, author)
    }

    private fun GetArticles.mapToArticleEntity(): ArticleEntity {
        val source = SourceEntity(source_id, source_name)
        return ArticleEntity(
            id,
            title,
            description,
            content,
            article_url,
            image_url,
            source,
            publish_date,
            author,
            isSaved = is_saved.toBoolean()
        )
    }

    private fun GetSavedArticles.mapToArticleEntity(): ArticleEntity {
        val source = SourceEntity(source_id, source_name)
        return ArticleEntity(
            id,
            title,
            description,
            content,
            article_url,
            image_url,
            source,
            publish_date,
            author,
            isSaved = is_saved.toBoolean()
        )
    }

    private fun ArticleEntity.toArticle(): comsalmannewsdata.table.Article {
        return comsalmannewsdata.table.Article(
            id = id,
            title = title,
            description = description,
            content = content,
            publish_date = publishedDate,
            author = author,
            is_saved = 0,
            article_url = articleUrl,
            image_url = imageUrl,
            source_name = source.name
        )
    }

    private fun Long.toBoolean() = equals(1)
}