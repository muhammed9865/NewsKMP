package com.salman.news.data.repository

import com.salman.news.data.source.remote.IssueRemoteDataSource
import com.salman.news.domain.repository.IssueRepository

/**
 * Created by Muhammed Salman email(mahmadslman@gmail.com) on 2/8/2024.
 */
class IssueRepositoryImpl(
    private val issueRemoteDataSource: IssueRemoteDataSource
): IssueRepository {
    override suspend fun sendIssue(email: String, description: String): Result<Unit> {
        val issueResult = issueRemoteDataSource.sendIssue(email, description)
        return if (issueResult.isSuccess) {
            // Handle the issue here if needed
            Result.success(Unit)
        } else {
            Result.failure(issueResult.exceptionOrNull()!!)
        }
    }
}