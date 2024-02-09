package com.salman.news.data.source.remote

/**
 * Created by Muhammed Salman email(mahmadslman@gmail.com) on 2/9/2024.
 */
interface FeedbackRemoteDataSource {
    suspend fun sendFeedback(platform: String, rating: Int, message: String): Result<Unit>
}