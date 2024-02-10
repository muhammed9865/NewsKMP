package com.salman.news.domain.usecases

import com.salman.news.domain.repository.PreferencesRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.IO
import kotlinx.coroutines.flow.flowOn

/**
 * Created by Muhammed Salman email(mahmadslman@gmail.com) on 2/9/2024.
 */
class GetScreenModeFlowUseCase(
    private val preferencesRepository: PreferencesRepository
) {

    operator fun invoke() = preferencesRepository.screenMode.flowOn(Dispatchers.IO)
}