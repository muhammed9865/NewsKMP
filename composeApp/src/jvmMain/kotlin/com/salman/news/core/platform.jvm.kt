package com.salman.news.core

/**
 * Created by Muhammed Salman email(mahmadslman@gmail.com) on 2/1/2024.
 */

object JVMPlatform: Platform {
    override val name: String
        get() = Platform.DESKTOP
}
actual fun getPlatform(): Platform {
    return JVMPlatform
}