package com.salman.news.domain.usecases

import com.salman.news.core.Resource
import com.salman.news.domain.model.Article
import com.salman.news.domain.model.SearchTimeFrame
import com.salman.news.domain.model.Suggestion
import com.salman.news.domain.model.SuggestionsGroup
import com.salman.news.domain.repository.SearchRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.withContext

/**
 * Created by Muhammed Salman email(mahmadslman@gmail.com) on 2/17/2024.
 */
class SearchByQueryUseCase(
    private val searchRepository: SearchRepository,
) {

    suspend operator fun invoke(query: String, timeFrame: SearchTimeFrame): Flow<Resource<List<SuggestionsGroup>>> {
        return flow {
            emit(Resource.Loading)
            try {
                val articlesResult = searchRepository.search(query, timeFrame)
                val suggestions = withContext(Dispatchers.Default) {
                    articlesResult.groupByDateAsSuggestions()
                }
                emit(Resource.Success(suggestions))
            } catch (e: Exception) {
                emit(Resource.Error(e))
            }
        }
    }

    private fun List<Article>.groupByDateAsSuggestions(): List<SuggestionsGroup> {
        return groupBy { it.publishDate.date }.map { (date, articles) ->
            val suggestions = articles.map { Suggestion(it.id, it.title, it.publishDate.toString(), it) }
            SuggestionsGroup(date.toString(), suggestions)
        }
    }
}