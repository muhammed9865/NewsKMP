package com.salman.news.presentation

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.compositionLocalOf
import cafe.adriel.voyager.navigator.Navigator
import cafe.adriel.voyager.transitions.SlideTransition
import com.salman.news.presentation.navigation.BottomNavigationScreen
import com.salman.news.presentation.theme.NewsTheme

var LocalTopNavigator = compositionLocalOf<Navigator> { error("Not initialized") }
@Composable
fun App() {
    NewsTheme(
        darkTheme = isSystemInDarkTheme(),
        dynamicColor = false
    ) {
        Navigator(BottomNavigationScreen()) {
            CompositionLocalProvider(LocalTopNavigator provides it) {
                SlideTransition(it)
            }
        }
    }
}