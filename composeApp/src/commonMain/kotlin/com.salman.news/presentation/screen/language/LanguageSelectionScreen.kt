package com.salman.news.presentation.screen.language

import androidx.compose.runtime.Composable
import cafe.adriel.voyager.core.screen.Screen
import com.salman.news.presentation.LocalTopNavigator
import com.salman.news.presentation.composables.ContainerWithError
import com.salman.news.presentation.composables.ScreenWithTopBar

/**
 * Created by Muhammed Salman email(mahmadslman@gmail.com) on 2/6/2024.
 */
class LanguageSelectionScreen : Screen {
    @Composable
    override fun Content() {
        val navigator = LocalTopNavigator.current
        ContainerWithError {
            ScreenWithTopBar(title = "Language", onBackPressed = { navigator.pop() }) {

            }
        }
    }
}