package com.salman.news.data.source.remote.impl

import com.salman.news.data.model.article.ArticlesDTO
import com.salman.news.data.source.remote.ArticlesRemoteDataSource
import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.request.get
import io.ktor.client.request.parameter
import io.ktor.http.parameters

/**
 * Created by Muhammed Salman email(mahmadslman@gmail.com) on 1/25/2024.
 */
class ArticlesRemoteDataSourceImpl(
    private val client: HttpClient
): ArticlesRemoteDataSource {
    override suspend fun getTopHeadlines(page: Int, countryCode: String): ArticlesDTO {
        return client.get("top-headlines") {
            parameter("country", countryCode)
        }.body()
    }
}