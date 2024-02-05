package com.salman.news.presentation.model

import com.salman.news.core.ImageUrlGenerator
import com.salman.news.data.source.local.IDHashGenerator
import com.salman.news.domain.model.Article
import com.salman.news.domain.model.ArticleSource
import kotlinx.coroutines.delay
import kotlinx.datetime.Clock
import kotlinx.datetime.TimeZone
import kotlinx.datetime.toLocalDateTime
import kotlin.random.Random

/**
 * Created by Muhammed Salman email(mahmadslman@gmail.com) on 1/30/2024.
 */
object ModelUtil {

    fun fakeArticles(): List<Article> {
        return List(10) {
            val title = randomString(it + 5 * Random.nextInt(1, 10))
            val source = ArticleSource(randomString(5), "Hello")
            val url = ImageUrlGenerator.get()
            val author = "Qassam"
            val id =
                IDHashGenerator.generate(title, source.name, url, author)
            Article(
                id,
                title,
                randomString(it + 5 * Random.nextInt(1, 10)),
                randomString(it + 10 * Random.nextInt(1, 10)),
                publishDate = Clock.System.now().toLocalDateTime(TimeZone.UTC),
                author,
                source,
                ImageUrlGenerator.get(),
                url,
                randomBoolean()
            )
        }
    }

    suspend fun remoteFakeArticles(delay: Long = 1000): List<Article> {
        delay(delay)
        return fakeArticles()
    }

    fun randomBoolean() = listOf(false, true).random()

    private fun randomString(words: Int): String {
        val lorem = "Lorem Ipsum Ipsum Lorem".split(" ")
        return (1..words).joinToString { lorem.random() }
    }
}