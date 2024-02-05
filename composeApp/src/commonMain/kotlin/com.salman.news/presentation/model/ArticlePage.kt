package com.salman.news.presentation.model

import com.salman.news.domain.model.Article

/**
 * Created by Muhammed Salman email(mahmadslman@gmail.com) on 2/2/2024.
 */
data class ArticlePage(
    val articles: List<Article>,
    val isInitialPage: Boolean = true,
    val page: Int
)
