package com.salman.news.presentation.screen.issue

import com.salman.news.core.CoroutineViewModel
import com.salman.news.domain.usecases.SendIssueUseCase
import com.salman.news.domain.usecases.ValidateIssueInputUseCase
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

/**
 * Created by Muhammed Salman email(mahmadslman@gmail.com) on 2/7/2024.
 */
class SendIssueViewModel(
    private val validateIssueInputUseCase: ValidateIssueInputUseCase,
    private val sendIssueUseCase: SendIssueUseCase
) : CoroutineViewModel() {

    private val mutableState = MutableStateFlow(SendIssueState())
    val state = mutableState.asStateFlow()

    fun onEmailChange(email: String) {
        mutableState.update {
            it.copy(email = email, error = null, isSent = false)
        }
    }

    fun onMessageChange(message: String) {
        mutableState.update {
            it.copy(message = message, error = null, isSent = false)
        }
    }

    fun onSendIssue() {
        scope.launch {
            mutableState.update {
                it.copy(isSending = true)
            }
            if (validateInput()) {
                sendIssue()
            }
            mutableState.update {
                it.copy(isSending = false)
            }
        }
    }

    private suspend fun validateInput(): Boolean {
        val state = mutableState.value
        val result = validateIssueInputUseCase(state.email, state.message)
        if (result.isFailure) {
            mutableState.update { it.copy(error = result.exceptionOrNull(), isSending = false) }
        }

        return result.isSuccess
    }

    private suspend fun sendIssue() {
        val state = mutableState.value
        val result = sendIssueUseCase(state.email, state.message)
        if (result.isFailure) {
            mutableState.update { it.copy(error = result.exceptionOrNull()) }
            return
        } else {
            mutableState.update { it.copy(isSent = true) }
        }
    }

    override fun onDispose() {
        super.onDispose()
        mutableState.update {
            SendIssueState()
        }
    }

}