package com.salman.news.logger

import kotlin.reflect.KClass

/**
 * Created by Muhammed Salman email(mahmadslman@gmail.com) on 2/5/2024.
 */
actual object Logger {
    private val logger
        get() = System.getLogger("logger")
    actual fun debug(clazz: Any, message: String) {
        logger.log(System.Logger.Level.DEBUG, message)
    }
    actual fun warn(clazz: Any, message: String, throwable: Throwable?) {
        logger.log(System.Logger.Level.WARNING, message, throwable)
    }
    actual fun error(clazz: Any, message: String, throwable: Throwable?) {
        logger.log(System.Logger.Level.ERROR, message, throwable)
    }
    actual fun wtf(clazz: Any, message: String) {
        error(clazz, message, null)
    }
}