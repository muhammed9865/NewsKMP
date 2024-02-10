package com.salman.news.domain.usecases

import com.salman.news.domain.model.ScreenMode
import com.salman.news.domain.repository.PreferencesRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.IO
import kotlinx.coroutines.withContext

/**
 * Created by Muhammed Salman email(mahmadslman@gmail.com) on 2/10/2024.
 */
class SetScreenModeUseCase(
    private val preferencesRepository: PreferencesRepository,
) {

    suspend operator fun invoke(screenMode: ScreenMode): Result<Unit> {
        return withContext(Dispatchers.IO) {
            preferencesRepository.setScreenMode(screenMode)
        }
    }
}