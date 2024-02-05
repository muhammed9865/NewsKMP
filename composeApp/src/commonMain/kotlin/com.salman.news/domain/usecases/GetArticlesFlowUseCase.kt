package com.salman.news.domain.usecases

import com.salman.news.domain.model.Article
import com.salman.news.domain.repository.ArticleRepository
import com.salman.news.presentation.model.ArticleUI
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.IO
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.withContext

/**
 * Created by Muhammed Salman email(mahmadslman@gmail.com) on 2/5/2024.
 */
class GetArticlesFlowUseCase(
    private val repository: ArticleRepository
) {
    suspend operator fun invoke(articlesWithOpenMenu: HashSet<Long>): Flow<List<ArticleUI>> {
        return withContext(Dispatchers.IO) {
            flow {
                repository.getArticlesFlow()
                    .map { articles ->
                        articles.map {
                            it.toUI(articlesWithOpenMenu)
                        }
                    }.collect { articlesUI ->
                        withContext(Dispatchers.Main) {
                            emit(articlesUI)
                        }
                    }
            }

        }
    }

    private fun Article.toUI(articlesWithOpenMenu: HashSet<Long>) = ArticleUI(
        article = this,
        isOptionsMenuOpen = articlesWithOpenMenu.contains(this.id)
    )
}