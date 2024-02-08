package com.salman.plugins

import com.salman.table.Issues
import com.salman.util.BuildUtil
import kotlinx.coroutines.Dispatchers
import org.jetbrains.exposed.sql.Database
import org.jetbrains.exposed.sql.SchemaUtils
import org.jetbrains.exposed.sql.transactions.experimental.newSuspendedTransaction
import org.jetbrains.exposed.sql.transactions.transaction

object DatabaseSingleton {

    fun init() {
        connect()
    }

    suspend fun <T> dbQuery(block: () -> T): T = newSuspendedTransaction(Dispatchers.IO) {
        block()
    }

    private fun connect() {
        val jdbcUrl = if (BuildUtil.isDevelopmentMode()) {
            "jdbc:h2://localhost:3306/dev_news_db"
        } else {
            "jdbc:h2://production:3306/prod_news_db"
        }
        val driver = "org.h2.Driver"
        println("Connecting to $jdbcUrl")

        // Connect to the database
        val database = Database.connect(jdbcUrl, driver)
        database.createTables()
    }

    private fun Database.createTables() {
        transaction(this) {
            SchemaUtils.create(Issues)
        }
    }
}