package com.salman.entity

import org.jetbrains.exposed.sql.Table

data class Issue(
    val id: Int,
    val email: String,
    val description: String,
    val issuedAt: Long
)

object Issues : Table() {
    val id = integer("id").autoIncrement()
    val email = varchar("email", 100)
    val description = varchar("description", 400)
    val issuedAt = long("issuedAt")

    override val primaryKey: PrimaryKey
        get() = PrimaryKey(id)
}
