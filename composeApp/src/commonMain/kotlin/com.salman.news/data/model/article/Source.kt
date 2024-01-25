package com.salman.news.data.model.article

import kotlinx.serialization.Serializable

@Serializable
data class Source(
    val id: String,
    val name: String
)