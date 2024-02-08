package com.salman.table

import org.jetbrains.exposed.sql.Table

object Issues : Table() {
    val id = integer("id").autoIncrement()
    val email = varchar("email", 100)
    val description = varchar("description", 400)
    val issuedAt = long("issuedAt")

    override val primaryKey: PrimaryKey
        get() = PrimaryKey(id)
}
