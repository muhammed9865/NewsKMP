package com.salman.news.data.source.remote.impl

import com.salman.news.data.source.remote.FeedbackRemoteDataSource
import com.salman.news.data.source.remote.constants.RemoteConstants
import com.salman.news.model.FeedbackRequest
import com.salman.news.model.url.EndPoints
import io.ktor.client.*
import io.ktor.client.request.*
import io.ktor.http.*

/**
 * Created by Muhammed Salman email(mahmadslman@gmail.com) on 2/9/2024.
 */
class FeedbackRemoteDataSourceImpl(
    private val baseClient: HttpClient,
    private val remoteConstants: RemoteConstants,
) : FeedbackRemoteDataSource {

    override suspend fun sendFeedback(platform: String, rating: Int, message: String): Result<Unit> {
        return runCatching {
            baseClient.post(url(EndPoints.SEND_FEEDBACK)) {
                setBody(FeedbackRequest(platform, rating, message))
                contentType(ContentType.Application.Json)
            }
        }
    }

    private fun url(endpoint: String): String {
        return remoteConstants.getServerBaseUrl() + EndPoints.FEEDBACK_ROUTE + endpoint
    }
}