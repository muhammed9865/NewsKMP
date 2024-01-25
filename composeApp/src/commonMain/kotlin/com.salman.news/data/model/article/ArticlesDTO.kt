package com.salman.news.data.model.article

import kotlinx.serialization.Serializable

@Serializable
data class ArticlesDTO(
    val articles: List<Article>,
    val status: String,
    val totalResults: Int
)