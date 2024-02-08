package com.salman.news.model

import kotlinx.serialization.Serializable

@Serializable
data class IssueRequest(
    val email: String,
    val description: String,
)
