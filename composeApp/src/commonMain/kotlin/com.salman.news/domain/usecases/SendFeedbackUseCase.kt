package com.salman.news.domain.usecases

import com.salman.news.core.getPlatform
import com.salman.news.domain.repository.FeedbackRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.IO
import kotlinx.coroutines.withContext

/**
 * Created by Muhammed Salman email(mahmadslman@gmail.com) on 2/9/2024.
 */
class SendFeedbackUseCase(
    private val feedbackRepository: FeedbackRepository
) {
    suspend operator fun invoke(rating: Int, message: String): Result<Unit> {
        val platform = getPlatform()
        return withContext(Dispatchers.IO) {
            feedbackRepository.sendFeedback(
                platform = platform.name,
                rating = rating,
                message = message,
            )
        }
    }
}