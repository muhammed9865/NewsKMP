package com.salman.news.logger

import kotlin.reflect.KClass

/**
 * Created by Muhammed Salman email(mahmadslman@gmail.com) on 2/5/2024.
 */
expect object Logger {
    fun debug(clazz: Any, message: String)
    fun warn(clazz: Any, message: String, throwable: Throwable? = null)
    fun error(clazz: Any, message: String, throwable: Throwable? = null)
    fun wtf(clazz: Any, message: String)
}