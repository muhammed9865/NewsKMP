package com.salman.news.data.source.remote

import io.ktor.client.engine.HttpClientEngine
import io.ktor.client.engine.darwin.Darwin

/**
 * Created by Muhammed Salman email(mahmadslman@gmail.com) on 1/25/2024.
 */
actual fun getHttpClient(): HttpClientEngine {
    return Darwin.create()
}