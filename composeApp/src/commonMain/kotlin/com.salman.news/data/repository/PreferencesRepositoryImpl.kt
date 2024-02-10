package com.salman.news.data.repository

import com.salman.news.data.mapper.toScreenMode
import com.salman.news.data.source.local.PreferencesLocalDataSource
import com.salman.news.domain.model.ScreenMode
import com.salman.news.domain.repository.PreferencesRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.map

/**
 * Created by Muhammed Salman email(mahmadslman@gmail.com) on 2/9/2024.
 */
class PreferencesRepositoryImpl(
    private val preferencesSource: PreferencesLocalDataSource
) : PreferencesRepository {

    override val screenMode: Flow<ScreenMode>
        get() = preferencesSource.getScreenModeFlow()
            .map { it.toScreenMode() }
            .catch { emit(ScreenMode.SYSTEM) }

    override suspend fun setScreenMode(screenMode: ScreenMode): Result<Unit> {
        return try {
            preferencesSource.setScreenMode(screenMode.name)
            Result.success(Unit)
        } catch (ex: Exception) {
            Result.failure(ex)
        }
    }
}