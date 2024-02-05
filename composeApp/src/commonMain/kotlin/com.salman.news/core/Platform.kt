package com.salman.news.core

interface Platform {
    val name: String

    companion object {
        const val ANDROID = "Android"
        const val IOS = "iOS"
        const val DESKTOP = "Desktop"
    }
}

expect fun getPlatform(): Platform