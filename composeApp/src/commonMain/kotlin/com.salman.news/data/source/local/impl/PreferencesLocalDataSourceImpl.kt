package com.salman.news.data.source.local.impl

import com.russhwolf.settings.ExperimentalSettingsApi
import com.russhwolf.settings.coroutines.FlowSettings
import com.salman.news.data.source.local.PreferencesLocalDataSource
import kotlinx.coroutines.flow.Flow

/**
 * Created by Muhammed Salman email(mahmadslman@gmail.com) on 2/9/2024.
 */
@OptIn(ExperimentalSettingsApi::class)
class PreferencesLocalDataSourceImpl(
    private val userSettings: FlowSettings,
) : PreferencesLocalDataSource {

    companion object {
        private const val SCREEN_MODE_KEY = "screen_mode"
    }

    override fun getScreenModeFlow(): Flow<String> {
        return userSettings.getStringFlow(SCREEN_MODE_KEY, "system")
    }

    override suspend fun setScreenMode(screenMode: String) {
        userSettings.putString(SCREEN_MODE_KEY, screenMode)
    }

}