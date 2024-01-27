package com.salman.news.data.source.remote

import io.ktor.client.engine.HttpClientEngine
import io.ktor.client.engine.cio.CIO

/**
 * Created by Muhammed Salman email(mahmadslman@gmail.com) on 1/26/2024.
 */
actual fun getHttpClient(): HttpClientEngine {
    return CIO.create()
}