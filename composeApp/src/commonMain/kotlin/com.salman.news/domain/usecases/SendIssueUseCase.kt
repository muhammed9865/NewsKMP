package com.salman.news.domain.usecases

import com.salman.news.domain.repository.IssueRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.IO
import kotlinx.coroutines.delay
import kotlinx.coroutines.withContext

/**
 * Created by Muhammed Salman email(mahmadslman@gmail.com) on 2/7/2024.
 */
class SendIssueUseCase(
    private val issueRepository: IssueRepository
) {

    suspend operator fun invoke(email: String, message: String): Result<Unit> {
        return withContext(Dispatchers.IO) {
            return@withContext issueRepository.sendIssue(email, message)
        }
    }
}