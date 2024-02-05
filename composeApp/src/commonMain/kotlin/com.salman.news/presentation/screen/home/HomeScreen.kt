package com.salman.news.presentation.screen.home

import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.text.intl.Locale
import cafe.adriel.voyager.core.screen.Screen
import cafe.adriel.voyager.koin.getScreenModel
import cafe.adriel.voyager.navigator.LocalNavigator
import cafe.adriel.voyager.navigator.currentOrThrow
import com.salman.news.presentation.composables.ArticlesList
import com.salman.news.presentation.composables.LoadingArticlesList
import com.salman.news.presentation.navigation.firstParent
import com.salman.news.presentation.screen.details.ArticleDetailsScreen

/**
 * Created by Muhammed Salman email(mahmadslman@gmail.com) on 1/25/2024.
 */
class HomeScreen : Screen {
    @Composable
    override fun Content() {
        val viewModel: HomeViewModel = getScreenModel()
        val navigator = LocalNavigator.currentOrThrow.firstParent
        val countryCode = Locale.current.region.lowercase()

        val state by viewModel.state.collectAsState()
        val articles = viewModel.articles

        if (state.hasLoadedInitialArticles.not()) {
            viewModel.loadInitialArticles(countryCode)
        }

        @Composable
        fun ArticlesList() {
            ArticlesList(
                articles = articles,
                isLoadingMoreArticles = state.isLoadingMoreArticles,
                onBookmarkClicked = { articleUI, _ -> viewModel.toggleArticleBookmark(articleUI) },
                onMuteSource = { articleUI, _ -> viewModel.muteSource(articleUI)},
                onMuteAuthor = { articleUI, _ -> viewModel.muteAuthor(articleUI)},
                onOptionsMenuClicked = { _, index -> viewModel.toggleArticleOptionsMenu(index) },
                onArticleClicked = { article, _ ->
                    navigator.push(ArticleDetailsScreen(article))
                }
            )
        }

        when {
            articles.isNotEmpty() -> { ArticlesList() }
            state.isLoadingInitialArticles -> LoadingArticlesList()
            state.hasLoadedInitialArticles -> { ArticlesList() }
        }
    }
}
