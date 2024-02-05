package com.salman.news.presentation.screen.bookmark

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.width
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.onGloballyPositioned
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.unit.dp
import cafe.adriel.voyager.core.screen.Screen
import cafe.adriel.voyager.koin.getScreenModel
import cafe.adriel.voyager.navigator.LocalNavigator
import cafe.adriel.voyager.navigator.tab.LocalTabNavigator
import com.salman.news.MR
import com.salman.news.presentation.composables.ArticlesList
import com.salman.news.presentation.composables.LoadingArticlesList
import com.salman.news.presentation.navigation.NavigationTab
import com.salman.news.presentation.navigation.firstParent
import com.salman.news.presentation.screen.details.ArticleDetailsScreen
import dev.icerock.moko.resources.compose.painterResource
import dev.icerock.moko.resources.compose.stringResource

/**
 * Created by Muhammed Salman email(mahmadslman@gmail.com) on 1/27/2024.
 */
class BookmarksScreen : Screen {
    @Composable
    override fun Content() {
        val tabNavigator = LocalTabNavigator.current
        val navigator = LocalNavigator.current?.firstParent
        val viewModel: BookmarkViewModel = getScreenModel()
        val articles by viewModel.articles.collectAsState(emptyList())
        val state by viewModel.state.collectAsState()

        when {
            state.areArticlesAvailable -> {
                ArticlesList(
                    modifier = Modifier.fillMaxSize(),
                    articles = articles,
                    isLoadingMoreArticles = false,
                    onMuteAuthor = { article, _ -> viewModel.muteAuthor(article) },
                    onMuteSource = { article, _ -> viewModel.muteSource(article) },
                    onOptionsMenuClicked = { _, index -> viewModel.toggleArticleOptionsMenu(index) },
                    onBookmarkClicked = { article, _ -> viewModel.toggleBookmark(article) },
                    onArticleClicked = { articleUI, _ ->
                        navigator?.push(
                            ArticleDetailsScreen(
                                articleUI
                            )
                        )
                    }
                )
            }

            state.isLoadingArticles -> {
                LoadingArticlesList()
            }

            state.noArticlesAvailable -> {
                NoArticlesBooked {
                    tabNavigator.current = NavigationTab.Home
                }
            }
        }
    }

    @Composable
    private fun NoArticlesBooked(
        modifier: Modifier = Modifier,
        onReadArticlesClicked: () -> Unit = {}
    ) {
        var buttonWidth by remember { mutableStateOf(0.dp) }
        val density = LocalDensity.current
        Column(
            modifier = modifier.fillMaxSize(),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            Image(
                painter = painterResource(MR.images.ic_sitting_reading),
                contentDescription = null,
            )
            Text(
                text = stringResource(MR.strings.empty_bookmarks),
                style = MaterialTheme.typography.bodyLarge,
                modifier = Modifier.onGloballyPositioned {
                    buttonWidth = with(density) {
                        it.size.width.toDp()
                    }
                }
            )
            OutlinedButton(
                onClick = onReadArticlesClicked,
                modifier = Modifier.width(buttonWidth),
                shape = MaterialTheme.shapes.small,
                contentPadding = PaddingValues(vertical = 3.dp)
            ) {
                Text(
                    stringResource(MR.strings.read_articles),
                    style = MaterialTheme.typography.bodyMedium
                )
            }
        }
    }
}