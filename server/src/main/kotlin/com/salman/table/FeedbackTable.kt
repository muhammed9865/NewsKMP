package com.salman.table

import org.jetbrains.exposed.sql.Table

object FeedbackTable : Table() {
    val id = integer("id").autoIncrement()
    val platform = varchar("platform", 100)
    val userRating = integer("user_rating")
    val note = varchar("note", 400)
    val issuedAt = long("issuedAt")
}