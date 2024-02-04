package com.salman.news.core

/**
* Created by Muhammed Salman email(mahmadslman@gmail.com) on 2/2/2024.
*/
sealed class Resource<out T> {
    data object Idle: Resource<Nothing>()
    data object Loading: Resource<Nothing>()
    data class Success<T>(val data: T): Resource<T>()
    data class Error(val throwable: Throwable?): Resource<Nothing>()
}
