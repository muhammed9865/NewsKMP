package com.salman.news.data.source.remote.model.article

import kotlinx.serialization.Serializable

@Serializable
data class Source(
    val id: String?,
    val name: String
)