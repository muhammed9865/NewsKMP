package com.salman.news.presentation.screen.mode

import com.salman.news.core.CoroutineViewModel
import com.salman.news.domain.model.ScreenMode
import com.salman.news.domain.usecases.GetScreenModeFlowUseCase
import com.salman.news.domain.usecases.SetScreenModeUseCase
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

/**
 * Created by Muhammed Salman email(mahmadslman@gmail.com) on 2/10/2024.
 */
class ScreenModeViewModel(
    private val getScreenModeFlowUseCase: GetScreenModeFlowUseCase,
    private val setScreenModeUseCase: SetScreenModeUseCase,
) : CoroutineViewModel() {

    private val mutableState = MutableStateFlow(ScreenModeState())
    val state = mutableState.asStateFlow()

    init {
        scope.launch {
            getScreenModeFlowUseCase().collect { screenMode ->
                mutableState.update {
                    it.copy(screenMode = screenMode, selectedMode = screenMode)
                }
            }
        }
    }

    fun onModeSelected(screenMode: ScreenMode) {
        mutableState.update {
            it.copy(selectedMode = screenMode)
        }
    }

    fun saveMode() {
        val mode = state.value.selectedMode
        scope.launch {
            mutableState.update { it.copy(isSaving = true) }
            saveMode(mode)
            mutableState.update { it.copy(isSaving = false) }
        }
    }

    fun hideSuccessMessage() {
        mutableState.update { it.copy(isSaved = false) }
    }

    private suspend fun saveMode(screenMode: ScreenMode) {
        val savingResult = setScreenModeUseCase(screenMode)
        if (savingResult.isSuccess) {
            mutableState.update { it.copy(isSaved = true) }
        } else {
            mutableState.update { it.copy(error = savingResult.exceptionOrNull()) }
        }
    }
}