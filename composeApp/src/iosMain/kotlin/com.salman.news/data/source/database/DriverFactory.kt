package com.salman.news.data.source.database

import app.cash.sqldelight.db.SqlDriver
import app.cash.sqldelight.driver.native.NativeSqliteDriver
import com.salman.news.data.source.database.DatabaseConstants.DATABASE_NAME
import com.salman.news.database.NewsDatabase

/**
 * Created by Muhammed Salman email(mahmadslman@gmail.com) on 1/26/2024.
 */
actual class DriverFactory {
    actual fun createDriver(): SqlDriver {
        return NativeSqliteDriver(NewsDatabase.Schema, DATABASE_NAME)
    }
}