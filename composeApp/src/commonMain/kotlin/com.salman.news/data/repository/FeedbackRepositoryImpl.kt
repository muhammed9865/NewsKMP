package com.salman.news.data.repository

import com.salman.news.data.source.remote.FeedbackRemoteDataSource
import com.salman.news.domain.repository.FeedbackRepository

/**
 * Created by Muhammed Salman email(mahmadslman@gmail.com) on 2/9/2024.
 */
class FeedbackRepositoryImpl(
    private val feedbackRemoteDataSource: FeedbackRemoteDataSource,
) : FeedbackRepository {
    override suspend fun sendFeedback(platform: String, rating: Int, message: String): Result<Unit> {
        return feedbackRemoteDataSource.sendFeedback(platform, rating, message)
    }
}