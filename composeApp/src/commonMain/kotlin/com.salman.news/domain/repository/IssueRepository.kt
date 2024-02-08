package com.salman.news.domain.repository

/**
 * Created by Muhammed Salman email(mahmadslman@gmail.com) on 2/8/2024.
 */
interface IssueRepository {
    suspend fun sendIssue(email: String, description: String): Result<Unit>
}