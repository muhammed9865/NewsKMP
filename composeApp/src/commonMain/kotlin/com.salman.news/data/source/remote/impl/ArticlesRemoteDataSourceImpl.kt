package com.salman.news.data.source.remote.impl

import com.salman.news.data.source.remote.ArticlesRemoteDataSource
import com.salman.news.data.source.remote.constants.RemoteConstants
import com.salman.news.data.source.remote.model.article.ArticlesDTO
import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.plugins.DefaultRequest
import io.ktor.client.request.get
import io.ktor.client.request.header
import io.ktor.client.request.parameter

/**
 * Created by Muhammed Salman email(mahmadslman@gmail.com) on 1/25/2024.
 */
class ArticlesRemoteDataSourceImpl(
    private val baseClient: HttpClient,
    private val remoteConstants: RemoteConstants
) : ArticlesRemoteDataSource {

    private val remoteClient by lazy {
        baseClient.config {
            install(DefaultRequest) {
                url(remoteConstants.getNewsApiBaseUrl())
                header("X-Api-Key", remoteConstants.getNewsApiKey())
            }
        }
    }

    override suspend fun getTopHeadlines(page: Int, countryCode: String): Result<ArticlesDTO> {
        return runCatching {
            val response = remoteClient.get("everything") {
                parameter("page", page)
                parameter("q", countryCode)
            }.body<ArticlesDTO>().filterRemoved()

            return Result.success(response)
        }
    }

    private fun ArticlesDTO.filterRemoved(): ArticlesDTO {
        val articles = articles.filterNot { it.isRemoved() }
        return copy(articles = articles)
    }
}