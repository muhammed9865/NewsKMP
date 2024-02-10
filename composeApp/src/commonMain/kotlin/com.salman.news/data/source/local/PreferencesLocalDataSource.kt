package com.salman.news.data.source.local

import kotlinx.coroutines.flow.Flow

/**
 * Created by Muhammed Salman email(mahmadslman@gmail.com) on 2/9/2024.
 */
interface PreferencesLocalDataSource {
     fun getScreenModeFlow(): Flow<String>
     suspend fun setScreenMode(screenMode: String)
}