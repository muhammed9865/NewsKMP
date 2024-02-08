package com.salman.service

import com.salman.news.model.Feedback
import com.salman.news.model.FeedbackRequest
import com.salman.plugins.DatabaseSingleton
import com.salman.table.FeedbackTable
import org.jetbrains.exposed.sql.ResultRow
import org.jetbrains.exposed.sql.insert

class FeedbackService {

    suspend fun insertFeedback(feedbackRequest: FeedbackRequest): Result<Feedback> {
        return DatabaseSingleton.dbQuery {
            val insertResult = FeedbackTable.insert {
                it[platform] = feedbackRequest.platform
                it[userRating] = feedbackRequest.userRating
                it[note] = feedbackRequest.note
                it[issuedAt] = System.currentTimeMillis()
            }

            val feedback = insertResult.resultedValues?.singleOrNull()?.toFeedback()
            if (feedback == null) {
                Result.failure(Exception("Failed to save feedback"))
            } else {
                Result.success(feedback)
            }
        }
    }


    private fun ResultRow.toFeedback() = Feedback(
        id = this[FeedbackTable.id],
        platform = this[FeedbackTable.platform],
        userRating = this[FeedbackTable.userRating],
        note = this[FeedbackTable.note],
        issuedAt = this[FeedbackTable.issuedAt]
    )
}