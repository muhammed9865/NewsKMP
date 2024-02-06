package com.salman.news.presentation.screen.bookmark

/**
 * Created by Muhammed Salman email(mahmadslman@gmail.com) on 2/5/2024.
 */
data class BookmarkState(
    val isLoadingArticles: Boolean = true,
    val noArticlesAvailable: Boolean = false
) {
    val areArticlesAvailable
        get() = isLoadingArticles.not() and noArticlesAvailable.not()
}
