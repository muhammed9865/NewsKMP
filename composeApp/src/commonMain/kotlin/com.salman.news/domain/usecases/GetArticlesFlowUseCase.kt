package com.salman.news.domain.usecases

import com.salman.news.domain.model.Article
import com.salman.news.domain.repository.ArticleRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.IO
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.channelFlow
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.withContext

/**
 * Created by Muhammed Salman email(mahmadslman@gmail.com) on 2/5/2024.
 */
class GetArticlesFlowUseCase(
    private val repository: ArticleRepository
) {
    suspend operator fun invoke(): Flow<List<Article>> {
        return channelFlow {
            repository.getArticlesFlow()
                .flowOn(Dispatchers.IO)
                .flowOn(Dispatchers.Default)
                .onEach { articlesUI ->
                    withContext(Dispatchers.Main) {
                        send(articlesUI)
                    }
                }
                .flowOn(Dispatchers.Main)
                .collect()
        }
    }
}