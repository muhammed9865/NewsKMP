package com.salman.news.presentation

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.runtime.Composable
import cafe.adriel.voyager.navigator.Navigator
import cafe.adriel.voyager.transitions.SlideTransition
import com.salman.news.presentation.navigation.BottomNavigationScreen
import com.salman.news.presentation.theme.NewsTheme

@Composable
fun App() {
    NewsTheme(
        darkTheme = isSystemInDarkTheme(),
        dynamicColor = false
    ) {
        Navigator(BottomNavigationScreen()) {
            SlideTransition(it)
        }
    }
}