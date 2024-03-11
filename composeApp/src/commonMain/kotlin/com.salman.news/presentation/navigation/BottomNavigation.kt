package com.salman.news.presentation.navigation

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.material3.TopAppBarScrollBehavior
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.unit.dp
import cafe.adriel.voyager.core.screen.Screen
import cafe.adriel.voyager.navigator.CurrentScreen
import cafe.adriel.voyager.navigator.tab.Tab
import cafe.adriel.voyager.navigator.tab.TabNavigator
import com.salman.news.MR
import com.salman.news.presentation.LocalTopNavigator
import com.salman.news.presentation.composables.TonalIconButton
import com.salman.news.presentation.screen.search.SearchScreen
import com.salman.news.presentation.theme.Dimens
import dev.icerock.moko.resources.compose.painterResource

/**
 * Created by Muhammed Salman email(mahmadslman@gmail.com) on 2/1/2024.
 */

class BottomNavigationScreen : Screen {
    @OptIn(ExperimentalMaterial3Api::class)
    @Composable
    override fun Content() {
        val topNavigator = LocalTopNavigator.current
        TabNavigator(NavigationTab.Home) { navigator ->
            val currentTab = navigator.currentOrNull
            val topAppbarScrollBehavior =
                TopAppBarDefaults.enterAlwaysScrollBehavior()
            Scaffold(
                modifier = Modifier.nestedScroll(topAppbarScrollBehavior.nestedScrollConnection),
                content = {
                    Column(
                        Modifier
                            .padding(it)
                    ) {
                        CurrentScreen()
                    }
                },
                topBar = {
                    TopAppBar(
                        currentTab!!,
                        topAppbarScrollBehavior,
                        navigateToSearch = { topNavigator.push(SearchScreen()) }
                    )
                },
                bottomBar = {
                    BottomAppBar()
                }
            )
        }
    }

    @OptIn(ExperimentalMaterial3Api::class)
    @Composable
    private fun TopAppBar(
        currentTab: Tab,
        scrollBehavior: TopAppBarScrollBehavior,
        navigateToSearch: () -> Unit = {},
    ) {
        val density = LocalDensity.current
        val windowInsets = WindowInsets(
            left = Dimens.ScreenPadding,
            right = Dimens.ScreenPadding,
            top = TopAppBarDefaults.windowInsets.getTop(density).dp,
            bottom = TopAppBarDefaults.windowInsets.getBottom(density).dp,
        )
        CenterAlignedTopAppBar(
            windowInsets = windowInsets,
            scrollBehavior = scrollBehavior,
            colors = TopAppBarDefaults.centerAlignedTopAppBarColors(containerColor = MaterialTheme.colorScheme.background),
            title = {
                Text(
                    text = currentTab.options.title,
                    style = MaterialTheme.typography.titleLarge,
                )
            },
            actions = {
                TonalIconButton(painter = painterResource(MR.images.ic_search)) {
                    navigateToSearch()
                }
            },
            navigationIcon = {
                Image(
                    painter = painterResource(MR.images.ic_secondary_avatar),
                    contentDescription = null,
                    modifier = Modifier
                        .size(40.dp)
                        .clip(CircleShape)
                        .clickable {  }
                )
            }
        )
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
                BottomNavigationItem(NavigationTab.More)
            }
        }
    }

}