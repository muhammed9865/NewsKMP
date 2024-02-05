package com.salman.news.data.source.remote.model.article

import kotlinx.serialization.Serializable

@Serializable
data class Article(
    val author: String?,
    val content: String?,
    val description: String?,
    val publishedAt: String,
    val source: Source,
    val title: String,
    val url: String,
    val urlToImage: String?,
) {

    fun isRemoved() = kotlin.run {
        listOf(description, author, title).any {
            it?.lowercase()?.contains("removed") == true
        }
    }
}