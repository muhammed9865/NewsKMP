package com.salman.news.presentation.model

import com.salman.news.domain.model.Article

/**
 * Created by Muhammed Salman email(mahmadslman@gmail.com) on 1/30/2024.
 */
data class ArticleUI(
    val article: Article,
    val isOptionsMenuOpen: Boolean = false,
)
