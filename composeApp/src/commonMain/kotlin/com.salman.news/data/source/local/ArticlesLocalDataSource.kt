package com.salman.news.data.source.local

import com.salman.news.data.source.local.entity.ArticleEntity
import com.salman.news.data.source.remote.model.article.Article
import com.salman.news.data.source.remote.model.article.Source
import kotlinx.coroutines.flow.Flow

/**
 * Created by Muhammed Salman email(mahmadslman@gmail.com) on 1/26/2024.
 */
interface ArticlesLocalDataSource {

    suspend fun getArticlesFlow(): Flow<List<ArticleEntity>>
    suspend fun getSavedArticlesFlow(): Flow<List<ArticleEntity>>

    suspend fun getArticleById(id: Long): ArticleEntity?
    suspend fun updateArticle(article: ArticleEntity)
    suspend fun insertArticles(articles: List<Article>)

    suspend fun insertSources(sources: List<Source>)
    suspend fun muteSource(source: Source)
    suspend fun muteAuthor(author: String)

}