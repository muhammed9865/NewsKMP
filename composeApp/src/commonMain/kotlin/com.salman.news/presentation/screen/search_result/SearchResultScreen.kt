package com.salman.news.presentation.screen.search_result

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.runtime.Composable
import androidx.compose.ui.unit.dp
import cafe.adriel.voyager.core.screen.Screen
import cafe.adriel.voyager.koin.getScreenModel
import com.salman.news.presentation.LocalTopNavigator
import com.salman.news.presentation.composables.ArticlesList
import com.salman.news.presentation.composables.ScreenWithTopBar

/**
 * Created by Muhammed Salman email(mahmadslman@gmail.com) on 2/23/2024.
 */
data class SearchResultScreen(val query: String) : Screen {
    @Composable
    override fun Content() {
        val navigator = LocalTopNavigator.current
        val viewModel: SearchResultViewModel = getScreenModel()
        ScreenWithTopBar(
            title = query,
            onBackPressed = { navigator.pop() },
            contentPadding = PaddingValues(0.dp)
        ) {
            ArticlesList(
                articles = viewModel.articles,
                onMuteSource = { item, _ -> viewModel.muteSource(item) },
                onMuteAuthor = { item, _ -> viewModel.muteAuthor(item) },
                onOptionsMenuClicked = { _, index -> viewModel.toggleArticleOptionsMenu(index) },
                onBookmarkClicked = { item, _ -> viewModel.toggleArticleBookmark(item) },
                onArticleClicked = { _, _ -> },
            )
        }
    }
}