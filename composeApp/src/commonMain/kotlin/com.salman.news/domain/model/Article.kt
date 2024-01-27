package com.salman.news.domain.model

import kotlinx.datetime.LocalDateTime

/**
 * Created by Muhammed Salman email(mahmadslman@gmail.com) on 1/25/2024.
 */
data class Article(
    val id: Long,
    val title: String,
    val description: String?,
    val content: String?,
    val publishDate: LocalDateTime,
    val author: String,
    val source: ArticleSource,
    val imageUrl: String,
    val isSaved: Boolean,
)
