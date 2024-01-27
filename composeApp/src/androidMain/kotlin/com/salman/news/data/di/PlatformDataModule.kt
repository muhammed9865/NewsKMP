package com.salman.news.data.di

import com.salman.news.data.source.database.DriverFactory
import com.salman.news.data.source.local.ArticlesLocalDataSource
import com.salman.news.data.source.local.impl.ArticlesLocalDataSourceImpl
import com.salman.news.database.NewsDatabase
import org.koin.core.module.Module
import org.koin.dsl.module

/**
 * Created by Muhammed Salman email(mahmadslman@gmail.com) on 1/26/2024.
 */
actual class PlatformDataModule {
    actual val module: Module = module {

        single {
            val sqlDriver = DriverFactory(get()).createDriver()
            NewsDatabase(sqlDriver)
        }

        single<ArticlesLocalDataSource> {
            ArticlesLocalDataSourceImpl(get())
        }
    }
}