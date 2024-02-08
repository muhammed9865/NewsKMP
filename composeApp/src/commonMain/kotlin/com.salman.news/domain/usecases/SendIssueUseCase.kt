package com.salman.news.domain.usecases

import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.IO
import kotlinx.coroutines.delay
import kotlinx.coroutines.withContext

/**
 * Created by Muhammed Salman email(mahmadslman@gmail.com) on 2/7/2024.
 */
class SendIssueUseCase {

    suspend operator fun invoke(email: String, message: String): Result<Unit> {
        return withContext(Dispatchers.IO) {
            delay(3000)
            Result.success(Unit)
        }
    }
}