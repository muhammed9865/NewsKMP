package com.salman.news.data.source.remote

import com.salman.news.model.IssueDTO

/**
 * Created by Muhammed Salman email(mahmadslman@gmail.com) on 2/8/2024.
 */
interface IssueRemoteDataSource {
    suspend fun sendIssue(email: String, description: String): Result<IssueDTO>
}