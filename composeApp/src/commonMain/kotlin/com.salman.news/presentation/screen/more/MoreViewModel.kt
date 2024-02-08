package com.salman.news.presentation.screen.more

import com.salman.news.core.CoroutineViewModel
import com.salman.news.logger.Logger
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update

/**
 * Created by Muhammed Salman email(mahmadslman@gmail.com) on 2/7/2024.
 */
class MoreViewModel : CoroutineViewModel() {

    private val _issueDialogVisible = MutableStateFlow(false)
    val issueDialogVisible = _issueDialogVisible.asStateFlow()

    fun toggleIssueDialogVisibility() {
        Logger.debug(this, "IssueDialogVisibility${_issueDialogVisible.value}")
        _issueDialogVisible.update {
            !it
        }
    }
}