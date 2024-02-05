package com.salman.news.logger

import android.util.Log
import kotlin.reflect.KClass

/**
 * Created by Muhammed Salman email(mahmadslman@gmail.com) on 2/5/2024.
 */

actual object Logger {
    actual fun debug(clazz: Any, message: String) {
        Log.d(clazz::class.simpleName, message)
    }
    actual fun warn(clazz: Any, message: String, throwable: Throwable?) {
        Log.w(clazz::class.simpleName, message, throwable)
    }
    actual fun error(clazz: Any, message: String, throwable: Throwable?) {
        Log.e(clazz::class.simpleName, message, throwable)
    }
    actual fun wtf(clazz: Any, message: String) {
        Log.wtf(clazz::class.simpleName, message)
    }
}