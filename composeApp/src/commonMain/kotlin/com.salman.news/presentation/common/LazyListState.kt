package com.salman.news.presentation.common

import androidx.compose.foundation.lazy.LazyListState

/**
 * Created by Muhammed Salman email(mahmadslman@gmail.com) on 2/2/2024.
 */

/**
 * Checks if the lazy list reached the item at [offset] counting from down to top
 * @param offset a threshold to see if we reached a given index from bottom to top.
 * @return if lastIndex - lastVisibleItemIndex <= [offset]
 */
fun LazyListState.hasReachedItemAt(offset: Int): Boolean {
    val lastVisibleItemIndex = layoutInfo.visibleItemsInfo.lastIndex
    val lastIndex = layoutInfo.totalItemsCount - 1

    println("LazyList: $lastVisibleItemIndex : $lastIndex")

    return lastIndex - lastVisibleItemIndex <= offset
}