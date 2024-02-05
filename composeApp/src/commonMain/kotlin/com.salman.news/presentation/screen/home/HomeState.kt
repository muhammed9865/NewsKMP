package com.salman.news.presentation.screen.home

/**
 * Created by Muhammed Salman email(mahmadslman@gmail.com) on 1/31/2024.
 */
data class HomeState(
    val isLoadingInitialArticles: Boolean = true,
    val isLoadingMoreArticles: Boolean = false,
    val failedToLoadInitialArticles: Boolean = false,
    val failedToLoadMoreArticles: Boolean = false
) {
    val hasLoadedInitialArticles
        get() = isLoadingInitialArticles.not() and failedToLoadInitialArticles.not()
}
