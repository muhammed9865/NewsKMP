package com.salman.news.presentation.screen.feedback

/**
 * Created by Muhammed Salman email(mahmadslman@gmail.com) on 2/9/2024.
 */
data class FeedbackState(
    val rating: Int = 0,
    val message: String = "",
    val error: Throwable? = null,
    val isSending: Boolean = false,
    val isSentSuccessfully: Boolean = false
) {
    val isSubmissionEnabled: Boolean
        get() = rating > 0 && message.isNotBlank()
}
