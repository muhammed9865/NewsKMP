package com.salman.news.core

interface Platform {
    val name: String
}

expect fun getPlatform(): Platform