package com.salman.news.presentation.screen.search

import com.salman.news.domain.model.SearchTimeFrame
import com.salman.news.domain.model.SuggestionsGroup

/**
 * Created by Muhammed Salman email(mahmadslman@gmail.com) on 2/24/2024.
 */
data class SearchState(
    val query: String = "",
    val selectedTimeFrame: SearchTimeFrame = SearchTimeFrame.Default,
    val suggestions: List<SuggestionsGroup> = emptyList(),
    val isLoading: Boolean = false,
    val error: Throwable? = null
)
