package com.salman.news.presentation.navigation

import cafe.adriel.voyager.navigator.Navigator
import cafe.adriel.voyager.navigator.tab.Tab
import cafe.adriel.voyager.navigator.tab.TabNavigator

/**
 * Created by Muhammed Salman email(mahmadslman@gmail.com) on 2/1/2024.
 */
val Navigator.firstParent: Navigator get() {
    val currentNavigator = this
    if (parent != null) {
        parent!!.firstParent
    }
    return currentNavigator
}

val TabNavigator.currentOrNull: Tab? get() {
    return try {
        current
    } catch (ex: ClassCastException) {
        null
    }
}