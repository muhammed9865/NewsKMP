package com.salman.news.domain.usecases

import com.salman.news.core.Resource
import com.salman.news.domain.model.ArticleSource
import com.salman.news.domain.repository.ArticleRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

/**
 * Created by Muhammed Salman email(mahmadslman@gmail.com) on 2/14/2024.
 */
class GetAllSourcesUseCase(
    private val articleRepository: ArticleRepository
) {
    suspend operator fun invoke(): Flow<Resource<List<ArticleSource>>> {
        return flow {
            emit(Resource.Loading)
            articleRepository.getAllSources().collect {
                emit(Resource.Success(it))
            }
        }
    }
}