package com.salman.news.data.source.database

import android.content.Context
import app.cash.sqldelight.db.SqlDriver
import app.cash.sqldelight.driver.android.AndroidSqliteDriver
import com.salman.news.database.NewsDatabase

/**
 * Created by Muhammed Salman email(mahmadslman@gmail.com) on 1/25/2024.
 *
 */
actual class DriverFactory(private val context: Context) {
    actual fun createDriver(): SqlDriver {
        return AndroidSqliteDriver(
            NewsDatabase.Schema,
            context,
            "news_database.db"
        )
    }
}
