package com.salman.news.core

import android.os.Build

class AndroidPlatform : Platform {
    override val name: String = Platform.ANDROID
}

actual fun getPlatform(): Platform = AndroidPlatform()