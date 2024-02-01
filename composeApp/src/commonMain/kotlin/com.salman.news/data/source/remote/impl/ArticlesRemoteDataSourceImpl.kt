package com.salman.news.data.source.remote.impl

import com.salman.news.data.source.remote.ArticlesRemoteDataSource
import com.salman.news.data.source.remote.model.article.ArticlesDTO
import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.request.get
import io.ktor.client.request.parameter

/**
 * Created by Muhammed Salman email(mahmadslman@gmail.com) on 1/25/2024.
 */
class ArticlesRemoteDataSourceImpl(
    private val client: HttpClient
) : ArticlesRemoteDataSource {
    override suspend fun getTopHeadlines(page: Int, countryCode: String): Result<ArticlesDTO> {
        return runCatching {
            val response = client.get("top-headlines") {
                parameter("page", page)
                parameter("country", countryCode)
            }.body<ArticlesDTO>().filterRemoved()


            return Result.success(response)
        }
    }

    private fun ArticlesDTO.filterRemoved(): ArticlesDTO {
        val articles = articles.filterNot { it.isRemoved() }
        return copy(articles = articles)
    }
}