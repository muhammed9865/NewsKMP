package com.salman.news.presentation.screen.details

import androidx.compose.runtime.Composable
import cafe.adriel.voyager.core.screen.Screen
import com.salman.news.domain.model.Article
import com.salman.news.presentation.composables.ArticleItem
import com.salman.news.presentation.model.ArticleUI

/**
 * Created by Muhammed Salman email(mahmadslman@gmail.com) on 1/31/2024.
 */
class ArticleDetailsScreen(
    private val article: ArticleUI
) : Screen {
    @Composable
    override fun Content() {
        ArticleItem(articleUi = article) {}
    }
}