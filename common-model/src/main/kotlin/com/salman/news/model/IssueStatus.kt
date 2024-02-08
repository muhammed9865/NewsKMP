package com.salman.news.model

import kotlinx.serialization.Serializable

@Serializable
enum class IssueStatus {
    PENDING,
    IN_PROGRESS,
    RESOLVED
}