package com.salman.news.presentation

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.BottomAppBar
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import cafe.adriel.voyager.navigator.CurrentScreen
import cafe.adriel.voyager.navigator.tab.TabNavigator
import com.salman.news.presentation.navigation.BottomNavigationItem
import com.salman.news.presentation.navigation.NavigationTab
import com.salman.news.presentation.theme.NewsTheme

@Composable
fun App() {
    NewsTheme(
        darkTheme = isSystemInDarkTheme(),
        dynamicColor = false
    ) {
        TabNavigator(NavigationTab.Home) {
            Scaffold(
                content = {
                    CurrentScreen()
                },
                bottomBar = {
                    BottomAppBar {
                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.SpaceAround
                        ) {
                            BottomNavigationItem(NavigationTab.Home)
                            BottomNavigationItem(NavigationTab.Bookmarks)
                            BottomNavigationItem(NavigationTab.Settings)
                        }
                    }
                }
            )
        }
    }
}