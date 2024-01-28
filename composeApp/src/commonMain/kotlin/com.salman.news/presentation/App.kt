package com.salman.news.presentation

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.BottomAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import cafe.adriel.voyager.navigator.CurrentScreen
import cafe.adriel.voyager.navigator.tab.LocalTabNavigator
import cafe.adriel.voyager.navigator.tab.TabNavigator
import com.salman.news.MR
import com.salman.news.presentation.composables.TonalIcon
import com.salman.news.presentation.navigation.BottomNavigationItem
import com.salman.news.presentation.navigation.NavigationTab
import com.salman.news.presentation.theme.NewsTheme
import dev.icerock.moko.resources.compose.painterResource

@Composable
fun App() {
    NewsTheme(
        darkTheme = isSystemInDarkTheme(),
        dynamicColor = false
    ) {
        TabNavigator(NavigationTab.Home) {
            Scaffold(
                content = { CurrentScreen() },
                topBar = { TopAppBar() },
                bottomBar = { BottomAppBar() }
            )
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun TopAppBar() {
    val navigator = LocalTabNavigator.current
    val currentTab = navigator.current
    Surface(shadowElevation = 4.dp) {
        TopAppBar(
            title = {
                Text(
                    modifier = Modifier.fillMaxWidth(),
                    text = currentTab.options.title,
                    style = MaterialTheme.typography.titleLarge,
                    textAlign = TextAlign.Center
                )
            },
            actions = {
                TonalIcon(painter = painterResource(MR.images.ic_search)) {
                    // TODO navigate to search screen
                }
            },
            navigationIcon = {
                TonalIcon(painter = painterResource(MR.images.ic_list))
            }
        )
    }
}

@Composable
private fun BottomAppBar() {
    BottomAppBar(
        containerColor = MaterialTheme.colorScheme.background
    ) {
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