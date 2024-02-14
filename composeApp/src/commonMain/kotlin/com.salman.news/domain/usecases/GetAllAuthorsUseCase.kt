package com.salman.news.domain.usecases

import com.salman.news.core.Resource
import com.salman.news.domain.model.ArticleAuthor
import com.salman.news.domain.repository.ArticleRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

/**
 * Created by Muhammed Salman email(mahmadslman@gmail.com) on 2/14/2024.
 */
class GetAllAuthorsUseCase(
    private val articleRepository: ArticleRepository
) {

    suspend operator fun invoke(): Flow<Resource<List<ArticleAuthor>>> {
        return flow {
            emit(Resource.Loading)
            articleRepository.getAllAuthors().collect {
                emit(Resource.Success(it))
            }
        }
    }
}