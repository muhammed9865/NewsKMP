package com.salman.news.data.source.remote.impl

import com.salman.news.data.source.remote.IssueRemoteDataSource
import com.salman.news.data.source.remote.constants.RemoteConstants
import com.salman.news.model.IssueDTO
import com.salman.news.model.IssueRequest
import com.salman.news.model.url.EndPoints
import io.ktor.client.*
import io.ktor.client.call.*
import io.ktor.client.plugins.*
import io.ktor.client.request.*
import io.ktor.http.*

/**
 * Created by Muhammed Salman email(mahmadslman@gmail.com) on 2/8/2024.
 */
class IssueRemoteDataSourceImpl(
    private val baseClient: HttpClient,
    private val remoteConstants: RemoteConstants,
): IssueRemoteDataSource {

    override suspend fun sendIssue(email: String, description: String): Result<IssueDTO> {
        return try {
            val issue = baseClient.post(url(EndPoints.SEND_ISSUE)) {
                setBody(IssueRequest(email, description))
                contentType(ContentType.Application.Json)
            }.body<IssueDTO>()
            Result.success(issue)
        } catch (e: Exception) {
            Result.failure(e)
        }
    }

    private fun url(endpoint: String): String {
        return remoteConstants.getServerBaseUrl() + EndPoints.ISSUE_ROUTE + endpoint
    }


}