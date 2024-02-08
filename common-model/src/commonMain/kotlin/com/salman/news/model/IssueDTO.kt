package com.salman.news.model

import kotlinx.serialization.Serializable

@Serializable
data class IssueDTO(
    val id: Int,
    val email: String,
    val description: String,
    val issueStatus: IssueStatus,
    val issuedAt: Long
)
