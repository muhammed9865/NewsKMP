package com.salman.news.data.di

import com.salman.news.data.repository.ArticleRepositoryImpl
import com.salman.news.data.repository.IssueRepositoryImpl
import com.salman.news.data.source.remote.ArticlesRemoteDataSource
import com.salman.news.data.source.remote.CustomHttpLogger
import com.salman.news.data.source.remote.IssueRemoteDataSource
import com.salman.news.data.source.remote.constants.RemoteConstants
import com.salman.news.data.source.remote.constants.RemoteConstantsImpl
import com.salman.news.data.source.remote.getHttpClient
import com.salman.news.data.source.remote.impl.ArticlesRemoteDataSourceImpl
import com.salman.news.data.source.remote.impl.IssueRemoteDataSourceImpl
import com.salman.news.domain.repository.ArticleRepository
import com.salman.news.domain.repository.IssueRepository
import io.ktor.client.HttpClient
import io.ktor.client.plugins.HttpTimeout
import io.ktor.client.plugins.contentnegotiation.ContentNegotiation
import io.ktor.client.plugins.logging.LogLevel
import io.ktor.client.plugins.logging.Logging
import io.ktor.serialization.kotlinx.json.json
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

    single<ArticleRepository> {
        ArticleRepositoryImpl(get(), get())
    }

    single<IssueRepository> {
        IssueRepositoryImpl(get())
    }
}