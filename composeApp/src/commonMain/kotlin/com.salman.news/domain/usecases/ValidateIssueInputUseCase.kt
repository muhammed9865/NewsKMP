package com.salman.news.domain.usecases

import com.salman.news.domain.exception.InvalidEmailException
import com.salman.news.domain.exception.InvalidIssueMessageException
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

/**
 * Created by Muhammed Salman email(mahmadslman@gmail.com) on 2/7/2024.
 */
class ValidateIssueInputUseCase {

    suspend operator fun invoke(email: String, message: String): Result<Unit> {
        return withContext(Dispatchers.Default) {
            when {
                !validateEmail(email) -> Result.failure(InvalidEmailException())
                !validateMessage(message) -> Result.failure(InvalidIssueMessageException())
                else -> Result.success(Unit)
            }
        }
    }

    private fun validateEmail(email: String): Boolean {
        val isEmpty = email.isBlank()
        val isNotValid = Regex("^[A-Za-z0-9+_.-]+@(.+)\$").matches(email).not()
        return !isEmpty && !isNotValid
    }

    private fun validateMessage(message: String): Boolean {
        val isEmpty = message.isBlank()
        return !isEmpty
    }
}