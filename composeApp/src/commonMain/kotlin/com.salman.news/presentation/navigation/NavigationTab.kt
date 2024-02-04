package com.salman.news.presentation.navigation

import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import cafe.adriel.voyager.navigator.tab.LocalTabNavigator
import cafe.adriel.voyager.navigator.tab.Tab
import cafe.adriel.voyager.navigator.tab.TabOptions
import com.salman.news.MR
import com.salman.news.presentation.screen.bookmark.BookmarksScreen
import com.salman.news.presentation.screen.home.HomeScreen
import com.salman.news.presentation.screen.settings.SettingsScreen
import dev.icerock.moko.resources.compose.painterResource
import dev.icerock.moko.resources.compose.stringResource

/**
 * Created by Muhammed Salman email(mahmadslman@gmail.com) on 1/27/2024.
 */
sealed class NavigationTab : Tab {
    @Composable
    fun isCurrentScreen(): Boolean {
        val tabNavigator = LocalTabNavigator.current
        return tabNavigator.currentOrNull == this
    }

    data object Home : NavigationTab() {
        @Composable
        override fun Content() {
            HomeScreen().Content()
        }

        override val options: TabOptions
            @Composable
            get() {
                val title = stringResource(MR.strings.home)
                val iconId = if (isCurrentScreen()) {
                    MR.images.ic_home_filled
                } else MR.images.ic_home_outlined
                val icon = painterResource(iconId)

                return remember {
                    TabOptions(0u, title, icon)
                }
            }
    }

    data object Bookmarks : NavigationTab() {
        @Composable
        override fun Content() {
            BookmarksScreen().Content()
        }


        override val options: TabOptions
            @Composable
            get() {
                val title = stringResource(MR.strings.bookmarks)
                val iconId = if (isCurrentScreen()) {
                    MR.images.ic_bookmark_filled
                } else MR.images.ic_bookmark_outlined
                val icon = painterResource(iconId)

                return remember {
                    TabOptions(1u, title, icon)
                }
            }
    }

    data object Settings : NavigationTab() {
        @Composable
        override fun Content() {
            SettingsScreen().Content()
        }

        override val options: TabOptions
            @Composable
            get() {
                val title = stringResource(MR.strings.settings)
                val iconId = if (isCurrentScreen()) {
                    MR.images.ic_settings_filled
                } else MR.images.ic_settings_outlined
                val icon = painterResource(iconId)

                return remember {
                    TabOptions(1u, title, icon)
                }
            }
    }

    companion object {
        val tabs: List<NavigationTab>
            get() = listOf(Home, Settings, Bookmarks)
    }
}