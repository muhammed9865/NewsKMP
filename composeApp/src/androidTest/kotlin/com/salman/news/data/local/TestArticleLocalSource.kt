package com.salman.news.data.local

import android.content.Context
import androidx.test.core.app.ApplicationProvider
import androidx.test.ext.junit.runners.AndroidJUnit4
import app.cash.sqldelight.db.SqlDriver
import app.cash.sqldelight.driver.android.AndroidSqliteDriver
import com.salman.news.data.source.local.ArticlesLocalDataSource
import com.salman.news.data.source.local.entity.ArticleEntity
import com.salman.news.data.source.local.impl.ArticlesLocalDataSourceImpl
import com.salman.news.data.source.remote.model.article.Article
import com.salman.news.data.source.remote.model.article.Source
import com.salman.news.database.NewsDatabase
import com.salman.news.presentation.model.ModelUtil
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.toList
import kotlinx.coroutines.launch
import kotlinx.coroutines.test.TestCoroutineScheduler
import kotlinx.coroutines.test.UnconfinedTestDispatcher
import kotlinx.coroutines.test.runTest
import org.junit.After
import org.junit.Assert
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith

/**
 * Created by Muhammed Salman email(mahmadslman@gmail.com) on 2/5/2024.
 */
@RunWith(AndroidJUnit4::class)
class TestArticleLocalSource {

    private lateinit var source: ArticlesLocalDataSource
    private lateinit var driver: SqlDriver
    private lateinit var database: NewsDatabase
    @OptIn(ExperimentalCoroutinesApi::class)
    private val testCoroutineContext = UnconfinedTestDispatcher(TestCoroutineScheduler())

    @Before
    fun setup() {
        val context = ApplicationProvider.getApplicationContext<Context>()
        driver = AndroidSqliteDriver(NewsDatabase.Schema, context, null) // in-memory database
        database = NewsDatabase(driver)
        source = ArticlesLocalDataSourceImpl(database)
    }

    @After
    fun teardown() {
        driver.close()
    }


    @Test
    fun getArticleByIdReturnsActualArticleEntity() = runTest {
        // Given
        val articles = ModelUtil.fakeArticles()
        val items = articles.map {
            Article(
                it.author,
                it.content,
                it.description,
                it.publishDate.toString(),
                Source(it.source.id, it.source.name),
                it.title,
                it.articleUrl,
                it.imageUrl
            )
        }

        // When
        source.insertArticles(items)
        source.insertSources(items.map { it.source })
        val entity = source.getArticleById(1)

        // Then
        Assert.assertTrue(articles.any { it.id == entity?.id})
    }

    @OptIn(ExperimentalCoroutinesApi::class)
    @Test
    fun addingNewArticlesTriggersTheGetArticlesFlow() = runTest {
        // Given
        val items = ModelUtil.fakeArticles().map {
            Article(
                it.author,
                it.content,
                it.description,
                it.publishDate.toString(),
                Source(it.source.id, it.source.name),
                it.title,
                it.articleUrl,
                it.imageUrl
            )
        }

        // When
        val returnedItems = mutableListOf<List<ArticleEntity>>()
        backgroundScope.launch(testCoroutineContext) {
            source.getArticlesFlow().toList(returnedItems)
        }
        source.insertArticles(items)

        // Then
        println(returnedItems)
        Assert.assertTrue(returnedItems.size > 0)
    }

    @Test
    fun addingNewSourcesTriggersTheArticlesFlow() = runTest {
        // Given
        val items = ModelUtil.fakeArticles().map {
            Article(
                it.author,
                it.content,
                it.description,
                it.publishDate.toString(),
                Source(it.source.id, it.source.name),
                it.title,
                it.articleUrl,
                it.imageUrl
            )
        }

        // When
        val returnedItems = mutableListOf<List<ArticleEntity>>()
        backgroundScope.launch(testCoroutineContext) {
            source.getArticlesFlow().toList(returnedItems)
        }
        source.insertSources(items.map { it.source })

        // Then
        println(returnedItems)
        Assert.assertTrue(returnedItems.size > 0)
    }

    @Test
    fun togglingBookmarkTriggersTheArticlesFlow() = runTest {
        // Given
        val fakeModels = ModelUtil.fakeArticles()
        val items = fakeModels.map {
            Article(
                it.author,
                it.content,
                it.description,
                it.publishDate.toString(),
                Source(it.source.id, it.source.name),
                it.title,
                it.articleUrl,
                it.imageUrl
            )
        }

        // When
        val returnedItems = mutableListOf<List<ArticleEntity>>()
        backgroundScope.launch(coroutineContext) {
            source.getArticlesFlow().toList(returnedItems)
        }
        source.insertArticles(items)
        source.insertSources(items.map { it.source })
        val article = source.getArticleById(fakeModels.first { !it.isSaved }.id)!!
        source.updateArticle(article)

        // Then
        println(returnedItems)
        Assert.assertTrue(returnedItems.size > 0)
    }
}