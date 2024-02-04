package com.salman.news.presentation.model

import com.salman.news.core.ImageUrlGenerator
import com.salman.news.domain.model.Article
import com.salman.news.domain.model.ArticleSource
import kotlinx.coroutines.delay
import kotlinx.datetime.Clock
import kotlinx.datetime.TimeZone
import kotlinx.datetime.toLocalDateTime
import kotlin.native.concurrent.ThreadLocal
import kotlin.random.Random



/**
 * Created by Muhammed Salman email(mahmadslman@gmail.com) on 1/30/2024.
 */
object ModelUtil {

    fun fakeArticles(idOffset: Long = 1): List<Article> {
        var id = idOffset
        return List(10) {
            Article(
                id++,
                randomString(it + 5 * Random.nextInt(1, 10)),
                randomString(it + 5 * Random.nextInt(1, 10)),
                randomString(it + 10 * Random.nextInt(1, 10)),
                publishDate = Clock.System.now().toLocalDateTime(TimeZone.UTC),
                "Qassam",
                ArticleSource("Id", "Hello"),
                ImageUrlGenerator.get(),
                randomBoolean()
            )
        }
    }

    suspend fun remoteFakeArticles(delay: Long = 1000, idOffset: Long = 1L): List<Article> {
        delay(delay)
        return fakeArticles(idOffset)
    }

    fun randomBoolean() = listOf(false, true).random()

    private fun randomString(words: Int): String {
        val lorem = "Lorem Ipsum Ipsum Lorem".split(" ")
        return (1..words).joinToString { lorem.random() }
    }
}