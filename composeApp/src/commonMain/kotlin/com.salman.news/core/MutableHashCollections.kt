package com.salman.news.core

/**
 * Created by Muhammed Salman email(mahmadslman@gmail.com) on 2/5/2024.
 */
fun <K, V> MutableMap<K, V>.addOrRemove(key: K, value: V) {
    if (containsKey(key)) {
        remove(key)
    } else {
        put(key, value)
    }
}

fun <E> HashSet<E>.addOrRemove(item: E) {
    if (contains(item)) {
        remove(item)
    } else {
        add(item)
    }
}