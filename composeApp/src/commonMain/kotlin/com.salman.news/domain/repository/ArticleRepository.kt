package com.salman.news.domain.repository

import com.salman.news.domain.model.Article
import com.salman.news.domain.model.ArticleSource
import kotlinx.coroutines.flow.Flow

/**
 * Created by Muhammed Salman email(mahmadslman@gmail.com) on 1/25/2024.
 */
interface ArticleRepository {

    suspend fun loadArticles(page: Int, countryCode: String)
    suspend fun getArticlesFlow(): Flow<List<Article>>
    suspend fun toggleArticleBookmark(id: Int)
    suspend fun muteSource(source: ArticleSource)
    suspend fun muteAuthor(author: String)
}