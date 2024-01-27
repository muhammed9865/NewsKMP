package com.salman.news.core

import kotlin.random.Random

/**
 * Created by Muhammed Salman email(mahmadslman@gmail.com) on 1/26/2024.
 */
object ImageUrlGenerator {
    fun get(): String {
        val defaultUrl = "https://picsum.photos"
        val randomWidth = Random.nextInt(200, 300)
        val randomHeight = Random.nextInt(200, 300)

        return "$defaultUrl/$randomWidth/$randomHeight"
    }
}