package com.salman.news.data.source.database

import app.cash.sqldelight.db.SqlDriver
import app.cash.sqldelight.driver.jdbc.sqlite.JdbcSqliteDriver
import com.salman.news.data.source.database.DatabaseConstants.DATABASE_NAME
import com.salman.news.database.NewsDatabase
import java.io.File

/**
 * Created by Muhammed Salman email(mahmadslman@gmail.com) on 1/26/2024.
 */
actual class DriverFactory {
    actual fun createDriver(): SqlDriver {
        val driver =  JdbcSqliteDriver("jdbc:sqlite:${DATABASE_NAME}")
        if (!File(DATABASE_NAME).exists())
        {
            NewsDatabase.Schema.create(driver)
        }
        return driver
    }
}