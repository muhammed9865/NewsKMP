package com.salman.news.data.source.local.entity

/**
 * Created by Muhammed Salman email(mahmadslman@gmail.com) on 1/26/2024.
 */
data class ArticleEntity(
    val id: Long,
    val title: String,
    val description: String?,
    val content: String?,
    val articleUrl: String,
    val imageUrl: String?,
    val source: SourceEntity,
    val publishedDate: String,
    val author: String?,
    val isSaved: Boolean,
)
