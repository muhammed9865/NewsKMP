package com.salman.news.domain.usecases

import com.salman.news.core.Resource
import com.salman.news.domain.model.Article
import com.salman.news.domain.model.SearchTimeFrame
import com.salman.news.domain.model.Suggestion
import com.salman.news.domain.model.SuggestionsGroup
import com.salman.news.domain.repository.BlockListRepository
import com.salman.news.domain.repository.SearchRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.withContext

/**
 * Created by Muhammed Salman email(mahmadslman@gmail.com) on 2/17/2024.
 */
class SearchByQueryUseCase(
    private val searchRepository: SearchRepository,
    private val blockListRepository: BlockListRepository,
) {

    suspend operator fun invoke(query: String, timeFrame: SearchTimeFrame): Flow<Resource<List<SuggestionsGroup>>> {
        return flow {
            emit(Resource.Loading)
            try {
                val articlesResult = searchRepository.search(query, timeFrame)
                val suggestions = withContext(Dispatchers.Default) {
                    articlesResult
                        .filterBlackListedArticles()
                        .groupByDateAsSuggestions()
                }
                emit(Resource.Success(suggestions))
            } catch (e: Exception) {
                emit(Resource.Error(e))
            }
        }
    }

    private suspend fun List<Article>.filterBlackListedArticles(): List<Article> {
        val blackList = blockListRepository.getBlackListedItems().first()
        val blockedAuthors = blackList.map { it.author }
        val blockedSources = blackList.map { it.source }

        return filter { article ->
            !blockedAuthors.contains(article.author) && !blockedSources.contains(article.source.name)
        }
    }

    private fun List<Article>.groupByDateAsSuggestions(): List<SuggestionsGroup> {
        return groupBy { it.publishDate.date }.map { (date, articles) ->
            val suggestions = articles.map { Suggestion(it.id, it.title, it.publishDate.toString(), it) }
            SuggestionsGroup(date.toString(), suggestions)
        }
    }
}