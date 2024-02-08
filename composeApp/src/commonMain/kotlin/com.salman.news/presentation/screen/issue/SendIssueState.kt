package com.salman.news.presentation.screen.issue

/**
 * Created by Muhammed Salman email(mahmadslman@gmail.com) on 2/7/2024.
 */
data class SendIssueState(
    val isSending: Boolean = false,
    val isSent: Boolean = false,
    val error: Throwable? = null,
    val email: String = "",
    val message: String = ""
)
