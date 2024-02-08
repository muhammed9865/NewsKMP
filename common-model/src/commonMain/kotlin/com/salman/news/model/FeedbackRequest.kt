package com.salman.news.model

import kotlinx.serialization.Serializable

@Serializable
data class FeedbackRequest(
    val platform: String,
    val userRating: Int,
    val note: String,
)
