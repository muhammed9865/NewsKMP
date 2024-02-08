package com.salman.util

object BuildUtil {

    fun isDevelopmentMode(): Boolean {
        return System.getenv("development") == "true"
    }
}