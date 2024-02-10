package com.salman.news.domain.repository

import com.salman.news.domain.model.ScreenMode
import kotlinx.coroutines.flow.Flow

/**
 * Created by Muhammed Salman email(mahmadslman@gmail.com) on 2/9/2024.
 */
interface PreferencesRepository {

    val screenMode: Flow<ScreenMode>
    suspend fun setScreenMode(screenMode: ScreenMode): Result<Unit>

}