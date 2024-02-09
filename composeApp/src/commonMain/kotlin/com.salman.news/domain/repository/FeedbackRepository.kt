package com.salman.news.domain.repository

/**
 * Created by Muhammed Salman email(mahmadslman@gmail.com) on 2/9/2024.
 */
interface FeedbackRepository {

    suspend fun sendFeedback(platform: String, rating: Int, message: String): Result<Unit>
}