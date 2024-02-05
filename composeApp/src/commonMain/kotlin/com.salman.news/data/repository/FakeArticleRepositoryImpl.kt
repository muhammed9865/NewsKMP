package com.salman.news.data.repository

import com.salman.news.domain.model.Article
import com.salman.news.domain.model.ArticleSource
import com.salman.news.domain.repository.ArticleRepository
import com.salman.news.presentation.model.ModelUtil
import io.ktor.util.collections.ConcurrentMap
import kotlinx.coroutines.Job
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.launch

/**
 * Created by Muhammed Salman email(mahmadslman@gmail.com) on 2/4/2024.
 */
class FakeArticleRepositoryImpl : ArticleRepository {

    private val itemsFlow = MutableSharedFlow<List<Article>>()
    private val items = ConcurrentMap<Int, List<Article>>()
    private var articleIdOffset: Long = 1

    override suspend fun loadArticles(page: Int, countryCode: String): Result<Unit> {
        items.getOrPut(page) {
            ModelUtil.remoteFakeArticles(2000).also { articles ->
                println(articles.map { it.id })
                articleIdOffset = articles.maxOf { it.id } + 1
            }
        }
        emit()
        return Result.success(Unit)
    }

    override suspend fun getArticlesFlow(): Flow<List<Article>> {
        return itemsFlow
    }

    override suspend fun toggleArticleBookmark(id: Long) {
        coroutineScope {
            val jobs = mutableListOf<Job>()
            items.forEach { (key, value) ->
                jobs += launch {
                    val itemIndex = value.indexOfFirst { it.id == id }
                    if (itemIndex != -1) {
                        val item = value[itemIndex]
                        val updatedItem = item.copy(isSaved = item.isSaved.not())
                        items[key] = value.map { if (it.id == id) updatedItem else it}
                        jobs.forEach { it.cancel() }
                    }
                }
            }
        }
        emit()
    }

    override suspend fun muteSource(source: ArticleSource) {
        // Not needed
    }

    override suspend fun muteAuthor(author: String) {
        // Not needed
    }

    private suspend fun emit() {
        val pagesGroupedInList = items.values.flatten()
        itemsFlow.emit(pagesGroupedInList)
    }
}