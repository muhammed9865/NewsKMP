package com.salman.news.data.source.remote.constants

/**
 * Created by Muhammed Salman email(mahmadslman@gmail.com) on 1/25/2024.
 *
 * Implement your own RemoteConstants and provide these information
 */
interface RemoteConstants {

    fun getNewsApiBaseUrl(): String
    fun getServerBaseUrl(): String
    fun getNewsApiKey(): String
}