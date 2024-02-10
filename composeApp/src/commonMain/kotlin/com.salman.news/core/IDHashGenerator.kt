package com.salman.news.core

/**
 * Created by Muhammed Salman email(mahmadslman@gmail.com) on 1/26/2024.
 */
object IDHashGenerator {

    fun generate(vararg elements: Any): Long {
        return elements.toList().hashCode().toLong()
    }
}