package com.salman.news.data.di

import com.salman.news.data.source.remote.ArticlesRemoteDataSource
import com.salman.news.data.source.remote.CustomHttpLogger
import com.salman.news.data.source.remote.constants.RemoteConstants
import com.salman.news.data.source.remote.constants.RemoteConstantsImpl
import com.salman.news.data.source.remote.getHttpClient
import com.salman.news.data.source.remote.impl.ArticlesRemoteDataSourceImpl
import io.ktor.client.HttpClient
import io.ktor.client.plugins.DefaultRequest
import io.ktor.client.plugins.HttpTimeout
import io.ktor.client.plugins.contentnegotiation.ContentNegotiation
import io.ktor.client.plugins.logging.DEFAULT
import io.ktor.client.plugins.logging.LogLevel
import io.ktor.client.plugins.logging.Logger
import io.ktor.client.plugins.logging.Logging
import io.ktor.client.request.header
import io.ktor.serialization.kotlinx.json.json
import kotlinx.serialization.json.Json
import org.koin.dsl.module

/**
 * Created by Muhammed Salman email(mahmadslman@gmail.com) on 1/25/2024.
 */

val dataModule = module {
    single<RemoteConstants> {
        RemoteConstantsImpl()
    }

    single {
        val constants: RemoteConstants = get()

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
            install(DefaultRequest) {
                url(constants.getBaseUrl())
                header("X-Api-Key", constants.getAPIKey())
            }

        }
    }

    single<ArticlesRemoteDataSource> {
        ArticlesRemoteDataSourceImpl(get())
    }
}