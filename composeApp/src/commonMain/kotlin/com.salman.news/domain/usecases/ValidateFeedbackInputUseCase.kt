package com.salman.news.domain.usecases

import com.salman.news.domain.exception.InvalidFeedbackMessageException
import com.salman.news.domain.exception.InvalidRatingException
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

/**
 * Created by Muhammed Salman email(mahmadslman@gmail.com) on 2/9/2024.
 */
class ValidateFeedbackInputUseCase {

    suspend operator fun invoke(rating: Int, message: String): Result<Unit> {
        return withContext(Dispatchers.Default) {
            when {
                !validateRating(rating) -> Result.failure(InvalidRatingException())
                !validateMessage(message) -> Result.failure(InvalidFeedbackMessageException())
                else -> Result.success(Unit)
            }
        }
    }

    private fun validateRating(rating: Int): Boolean {
        return rating > 0
    }

    private fun validateMessage(message: String): Boolean {
        return true // No validation required since this field isn't mandatory
    }
}