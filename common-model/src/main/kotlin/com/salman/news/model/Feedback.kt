package com.salman.news.model

import kotlinx.serialization.Serializable

@Serializable
data class Feedback(
    val id: Int,
    val platform: String,
    val userRating: Int,
    val note: String,
    val issuedAt: Long
)
