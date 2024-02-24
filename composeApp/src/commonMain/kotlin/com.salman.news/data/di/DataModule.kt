package com.salman.news.data.di

import com.russhwolf.settings.ExperimentalSettingsApi
import com.russhwolf.settings.ObservableSettings
import com.russhwolf.settings.Settings
import com.russhwolf.settings.coroutines.toFlowSettings
import com.salman.news.data.repository.ArticleRepositoryImpl
import com.salman.news.data.repository.BlockListRepositoryImpl
import com.salman.news.data.repository.FeedbackRepositoryImpl
import com.salman.news.data.repository.IssueRepositoryImpl
import com.salman.news.data.repository.PreferencesRepositoryImpl
import com.salman.news.data.repository.SearchRepositoryImpl
import com.salman.news.data.source.local.BlockListLocalDataSource
import com.salman.news.data.source.local.PreferencesLocalDataSource
import com.salman.news.data.source.local.impl.BlockListLocalDataSourceImpl
import com.salman.news.data.source.local.impl.PreferencesLocalDataSourceImpl
import com.salman.news.data.source.remote.*
import com.salman.news.data.source.remote.constants.RemoteConstants
import com.salman.news.data.source.remote.constants.RemoteConstantsImpl
import com.salman.news.data.source.remote.impl.ArticlesRemoteDataSourceImpl
import com.salman.news.data.source.remote.impl.FeedbackRemoteDataSourceImpl
import com.salman.news.data.source.remote.impl.IssueRemoteDataSourceImpl
import com.salman.news.domain.repository.ArticleRepository
import com.salman.news.domain.repository.BlockListRepository
import com.salman.news.domain.repository.FeedbackRepository
import com.salman.news.domain.repository.IssueRepository
import com.salman.news.domain.repository.PreferencesRepository
import com.salman.news.domain.repository.SearchRepository
import io.ktor.client.*
import io.ktor.client.plugins.*
import io.ktor.client.plugins.contentnegotiation.*
import io.ktor.client.plugins.logging.*
import io.ktor.serialization.kotlinx.json.*
import kotlinx.serialization.json.Json
import org.koin.dsl.module

/**
 * Created by Muhammed Salman email(mahmadslman@gmail.com) on 1/25/2024.
 */

val sharedDataModule = module {
    single<RemoteConstants> {
        RemoteConstantsImpl()
    }

    single {
        HttpClient(engine = getHttpClient()) {
            install(ContentNegotiation) {
                json(json = Json {
                    ignoreUnknownKeys = true
                })
            }
            install(Logging) {
                logger = CustomHttpLogger()
                level = LogLevel.ALL
            }
            install(HttpTimeout) {
                connectTimeoutMillis = 10000
                requestTimeoutMillis = 10000
            }
        }
    }

    single<ArticlesRemoteDataSource> {
        ArticlesRemoteDataSourceImpl(get(), get())
    }

    single<IssueRemoteDataSource> {
        IssueRemoteDataSourceImpl(get(), get())
    }

    single<FeedbackRemoteDataSource> {
        FeedbackRemoteDataSourceImpl(get(), get())
    }

    @OptIn(ExperimentalSettingsApi::class)
    single<PreferencesLocalDataSource> {
        val settings = Settings()
        val flowSettings = (settings as ObservableSettings).toFlowSettings()
        PreferencesLocalDataSourceImpl(flowSettings)
    }

    single<BlockListLocalDataSource> {
        BlockListLocalDataSourceImpl(get())
    }

    single<ArticleRepository> {
        ArticleRepositoryImpl(get(), get())
    }

    single<IssueRepository> {
        IssueRepositoryImpl(get())
    }

    single<FeedbackRepository> {
        FeedbackRepositoryImpl(get())
    }

    single<PreferencesRepository> {
        PreferencesRepositoryImpl(get())
    }

    single<BlockListRepository> {
        BlockListRepositoryImpl(get())
    }

    single<SearchRepository> {
        SearchRepositoryImpl(get())
    }
}