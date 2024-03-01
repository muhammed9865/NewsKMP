package com.salman.news.domain.model

/**
 * Created by Muhammed Salman email(mahmadslman@gmail.com) on 2/17/2024.
 */
data class Suggestion(
    val id: Long,
    val title: String,
    val date: String,
    val associatedArticle: Article,
)
