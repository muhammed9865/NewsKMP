package com.salman.news.core

import cafe.adriel.voyager.core.model.ScreenModel
import cafe.adriel.voyager.core.model.screenModelScope
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.IO
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

/**
 * Created by Muhammed Salman email(mahmadslman@gmail.com) on 1/30/2024.
 */
abstract class CoroutineViewModel : ScreenModel {
    protected val scope: CoroutineScope
        get() = screenModelScope
    fun CoroutineScope.launchIO(block: suspend CoroutineScope.() -> Unit) = launch(
        context = Dispatchers.IO,
        block = block
    )

    fun CoroutineScope.launchMain(block: suspend CoroutineScope.() -> Unit) = launch(
        context = Dispatchers.Main,
        block = block
    )

    fun CoroutineScope.launchDefault(block: suspend CoroutineScope.() -> Unit) = launch(
        context = Dispatchers.Default,
        block = block
    )

    suspend inline fun <T> onIO(noinline block: suspend CoroutineScope.() -> T) = withContext(Dispatchers.IO, block)
}