package com.salman.service

import com.salman.news.model.Issue
import com.salman.news.model.IssueRequest
import com.salman.news.model.IssueStatus
import com.salman.plugins.DatabaseSingleton
import com.salman.table.Issues
import org.jetbrains.exposed.sql.ResultRow
import org.jetbrains.exposed.sql.insert
import org.jetbrains.exposed.sql.select

class IssueService {

    suspend fun saveIssue(issueRequest: IssueRequest): Result<Issue> {
        return DatabaseSingleton.dbQuery {
            val insertResult = Issues.insert {
                it[email] = issueRequest.email
                it[description] = issueRequest.description
                it[issuedAt] = System.currentTimeMillis()
            }

            val issue = insertResult.resultedValues?.singleOrNull()?.toIssue()
            if (issue == null) {
                Result.failure(Exception("Failed to save issue"))
            } else {
                Result.success(issue)
            }
        }
    }

    suspend fun getIssueByID(id: Int): Issue? {
        return DatabaseSingleton.dbQuery {
            Issues.select {
                Issues.id.eq(id)
            }.mapNotNull {
                it.toIssue()
            }.singleOrNull()
        }
    }

    private fun ResultRow.toIssue() = Issue(
        id = this[Issues.id],
        email = this[Issues.email],
        description = this[Issues.description],
        issueStatus = IssueStatus.PENDING,
        issuedAt = this[Issues.issuedAt]
    )
}