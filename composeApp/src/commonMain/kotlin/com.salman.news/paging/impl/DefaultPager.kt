package com.salman.news.paging.impl

import com.salman.news.paging.Pager
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.IO
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import kotlin.jvm.JvmInline

/**
 * Created by Muhammed Salman email(mahmadslman@gmail.com) on 2/2/2024.
 */
open class DefaultPager<Key, ItemType>(
    private val initialKey: Key,
    private inline val onLoadingUpdated: (Boolean) -> Unit,
    private inline val onRequest: suspend (nextKey: Key) -> Result<List<ItemType>>,
    private inline val onSuccess: (items: List<ItemType>, nextKey: Key, isInitialPage: Boolean) -> Unit,
    private inline val onError: (Throwable?) -> Unit,
    private inline val getNextKey: (currentKey: Key) -> Key
) : Pager<Key, ItemType> {

    private var currentKey = initialKey
    private var requestJob: Job? = null
    @OptIn(ExperimentalCoroutinesApi::class)
    private val mutex = Dispatchers.IO.limitedParallelism(1)

    override suspend fun loadNextItems() {
        if (requestJob?.isActive == true) {
            return
        }
        onLoadingUpdated(true)
        withContext(mutex) {
            launch {
                println("Logger: Started loading next items")
                val result = onRequest(currentKey).getOrElse {
                    println("Logger: Loading items failed $it")
                    onLoadingUpdated(false)
                    onError(it)
                    return@launch
                }

                println("Logger: Loading items succeeded")
                onLoadingUpdated(false)
                val isInitialPage = currentKey == initialKey
                currentKey = getNextKey(currentKey)
                onSuccess(result, currentKey, isInitialPage)
                cancel()
            }
        }
    }

    override suspend fun reset() {
        currentKey = initialKey
    }

    override fun cancel() {
        requestJob?.cancel()
        requestJob = null
    }
}