package com.salman.news.presentation.screen.mode

import com.salman.news.domain.model.ScreenMode

/**
 * Created by Muhammed Salman email(mahmadslman@gmail.com) on 2/10/2024.
 */
data class ScreenModeState(
    val screenMode: ScreenMode = ScreenMode.SYSTEM,
    val selectedMode: ScreenMode = ScreenMode.SYSTEM,
    val error: Throwable? = null,
    val isSaving: Boolean = false,
    val isSaved: Boolean = false,
) {
    val isSettingModeEnabled
        get() = !isSaving && screenMode != selectedMode
}
