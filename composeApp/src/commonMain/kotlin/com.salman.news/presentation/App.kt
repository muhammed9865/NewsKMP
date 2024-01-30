package com.salman.news.presentation

import androidx.compose.foundation.background
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.unit.dp
import cafe.adriel.voyager.navigator.CurrentScreen
import cafe.adriel.voyager.navigator.tab.LocalTabNavigator
import cafe.adriel.voyager.navigator.tab.TabNavigator
import com.salman.news.MR
import com.salman.news.presentation.composables.TonalIconButton
import com.salman.news.presentation.navigation.BottomNavigationItem
import com.salman.news.presentation.navigation.NavigationTab
import com.salman.news.presentation.theme.Dimens
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
                content = {
                    Box(Modifier.fillMaxSize().padding(it)) {
                        CurrentScreen()
                    }
                },
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
    val density = LocalDensity.current
    val windowInsets = WindowInsets(
        left = Dimens.ItemsPadding,
        right = Dimens.ItemsPadding,
        top = TopAppBarDefaults.windowInsets.getTop(density).dp,
        bottom = TopAppBarDefaults.windowInsets.getBottom(density).dp,
    )
    Surface(shadowElevation = 4.dp) {
        CenterAlignedTopAppBar(
            windowInsets = windowInsets,
            title = {
                Text(
                    text = currentTab.options.title,
                    style = MaterialTheme.typography.titleLarge,
                )
            },
            actions = {
                TonalIconButton(painter = painterResource(MR.images.ic_search)) {
                    // TODO navigate to search screen
                }
            },
            navigationIcon = {
                TonalIconButton(painter = painterResource(MR.images.ic_list))
            }
        )
    }
}

@Composable
private fun BottomAppBar() {
    Box(
        modifier = Modifier.fillMaxWidth()
            .background(MaterialTheme.colorScheme.background)
            .padding(vertical = Dimens.ItemsPadding)
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