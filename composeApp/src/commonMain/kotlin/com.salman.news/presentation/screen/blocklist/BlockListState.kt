package com.salman.news.presentation.screen.blocklist

import com.salman.news.core.Resource
import com.salman.news.domain.model.ArticleAuthor
import com.salman.news.domain.model.ArticleSource
import com.salman.news.domain.model.BlockListedItem

/**
 * Created by Muhammed Salman email(mahmadslman@gmail.com) on 2/10/2024.
 */
data class BlockListState(
    val blockedSources: Resource<List<BlockListedItem>> = Resource.Loading,
    val blockedAuthors: Resource<List<BlockListedItem>> = Resource.Loading,
    val allAuthors: Resource<List<ArticleAuthor>> = Resource.Loading,
    val allSources: Resource<List<ArticleSource>> = Resource.Loading,
    val authorSearchQuery: String = "",
    val sourceSearchQuery: String = "",
    val error: String = "",
    val showBlockNewItems: Boolean = false,
) {
    fun isBlockListEmpty(): Boolean {
        val isBlockedSourcesListEmpty = (blockedSources as? Resource.Success)?.data?.isEmpty() ?: true
        val isBlockedAuthorsListEmpty = (blockedAuthors as? Resource.Success)?.data?.isEmpty() ?: true
        return isBlockedAuthorsListEmpty and isBlockedSourcesListEmpty
    }
}
