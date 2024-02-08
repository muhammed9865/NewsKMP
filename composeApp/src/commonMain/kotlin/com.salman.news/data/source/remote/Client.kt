package com.salman.news.data.source.remote

import io.ktor.client.engine.HttpClientEngine
import io.ktor.client.plugins.logging.Logger

/**
 * Created by Muhammed Salman email(mahmadslman@gmail.com) on 1/25/2024.
 */
expect  fun getHttpClient(): HttpClientEngine

internal class CustomHttpLogger : Logger {
    override fun log(message: String) {
        com.salman.news.logger.Logger.debug(this, message)
    }
}