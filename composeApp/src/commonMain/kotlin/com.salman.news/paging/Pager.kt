package com.salman.news.paging

/**
 * Created by Muhammed Salman email(mahmadslman@gmail.com) on 2/2/2024.
 */
interface Pager<Key, ItemType> {

    /**
     * Loads the next page of [ItemType] given the [Key]
     */
    suspend fun loadNextItems()

    /**
     * Resets the [Key] to initial state and calls [cancel]
     */
    suspend fun reset()

    /**
     * Cancels the ongoing [loadNextItems] calls
     */
    fun cancel()
}