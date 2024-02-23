package com.salman.news.presentation

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.compositionLocalOf
import androidx.compose.runtime.getValue
import cafe.adriel.voyager.navigator.Navigator
import cafe.adriel.voyager.transitions.SlideTransition
import com.salman.news.domain.model.ScreenMode
import com.salman.news.domain.usecases.GetScreenModeFlowUseCase
import com.salman.news.presentation.navigation.BottomNavigationScreen
import com.salman.news.presentation.theme.NewsTheme
import org.koin.core.component.KoinComponent
import org.koin.core.component.inject
import org.koin.dsl.module

var LocalTopNavigator = compositionLocalOf<Navigator> { error("Not initialized") }
val appModule = module {
    factory { AppEntryContent() }
}

class AppEntryContent: KoinComponent {

    private val screenModeFlow: GetScreenModeFlowUseCase by inject()

    @Composable
    fun Content() {
        val screenModeState by screenModeFlow().collectAsState(initial = null)
        val screenModeIsDark = when (screenModeState) {
            ScreenMode.LIGHT -> false
            ScreenMode.DARK -> true
            else -> isSystemInDarkTheme()
        }
        NewsTheme(
            darkTheme = screenModeIsDark,
            dynamicColor = false
        ) {
            Navigator(BottomNavigationScreen()) {
                CompositionLocalProvider(LocalTopNavigator provides it) {
                    SlideTransition(it)
                }
            }
        }
    }
}