package com.salman.news.presentation.screen.feedback

import com.salman.news.core.CoroutineViewModel
import com.salman.news.domain.usecases.SendFeedbackUseCase
import com.salman.news.domain.usecases.ValidateFeedbackInputUseCase
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

/**
 * Created by Muhammed Salman email(mahmadslman@gmail.com) on 2/9/2024.
 */
class FeedbackViewModel(
    private val validateFeedbackInputUseCase: ValidateFeedbackInputUseCase,
    private val sendFeedbackUseCase: SendFeedbackUseCase,
) : CoroutineViewModel() {

    private val mutableState = MutableStateFlow(FeedbackState())
    val state = mutableState.asStateFlow()


    fun onRatingChanged(rating: Int) {
        mutableState.update {
            it.copy(rating = rating, error = null)
        }
    }

    fun onMessageChanged(message: String) {
        mutableState.update {
            it.copy(message = message, error = null)
        }
    }

    fun sendFeedback() {
        val currentState = state.value
        if (currentState.isSending) return

        scope.launch {
            if (validateInputs()) {
                mutableState.update {
                    it.copy(isSending = true)
                }
                sendFeedbackRequest()
            }
        }
    }

    private suspend fun validateInputs(): Boolean {
        val currentState = state.value
        val validationResult = validateFeedbackInputUseCase(currentState.rating, currentState.message)
        if (validationResult.isFailure) {
            mutableState.update {
                it.copy(error = validationResult.exceptionOrNull())
            }
            return false
        }
        return true
    }

    private suspend fun sendFeedbackRequest() {
        val currentState = state.value
        val result = sendFeedbackUseCase(currentState.rating, currentState.message)
        mutableState.update {
            it.copy(
                error = result.exceptionOrNull(),
                isSending = false,
                isSentSuccessfully = result.isSuccess
            )
        }
    }
}