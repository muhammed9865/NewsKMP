package com.salman.plugins

import com.salman.table.FeedbackTable
import com.salman.table.Issues
import kotlinx.coroutines.Dispatchers
import org.jetbrains.exposed.sql.Database
import org.jetbrains.exposed.sql.SchemaUtils
import org.jetbrains.exposed.sql.transactions.experimental.newSuspendedTransaction
import org.jetbrains.exposed.sql.transactions.transaction

object DatabaseSingleton {
    private const val DB_NAME = "news_db"
    private const val DRIVER_CLASS_NAME = "org.h2.Driver"
    private const val JDBC_URL = "jdbc:h2:file:./build/database/$DB_NAME"

    fun init() {
        connect()
    }

    suspend fun <T> dbQuery(block: () -> T): T = newSuspendedTransaction(Dispatchers.IO) {
        block()
    }

    private fun connect() {
        println("Connecting to $JDBC_URL")
        // Connect to the database
        val database = Database.connect(JDBC_URL, DRIVER_CLASS_NAME)
        database.createTables()
    }

    private fun Database.createTables() {
        transaction(this) {
            SchemaUtils.create(Issues, FeedbackTable)
        }
    }
}