package com.salman.news.domain.usecases

import com.salman.news.domain.model.Article
import com.salman.news.domain.repository.ArticleRepository
import com.salman.news.presentation.model.ArticleUI
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.IO
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.channelFlow
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.withContext

/**
 * Created by Muhammed Salman email(mahmadslman@gmail.com) on 2/5/2024.
 */
class GetBookmarkedArticlesFlowUseCase(
    private val repository: ArticleRepository
) {
    operator fun invoke(articlesWithOpenMenu: HashSet<Long>): Flow<List<ArticleUI>> {
        return channelFlow {
            repository.getBookmarkedArticlesFlow()
                .flowOn(Dispatchers.IO)
                .map { articles ->
                    articles.map {
                        it.toUI(articlesWithOpenMenu)
                    }
                }.flowOn(Dispatchers.Default)
                .onEach { articlesUI ->
                    withContext(Dispatchers.Main) {
                        send(articlesUI)
                    }
                }.flowOn(Dispatchers.Main)
                .collect()
        }
    }

    private fun Article.toUI(articlesWithOpenMenu: HashSet<Long>) = ArticleUI(
        article = this,
        isOptionsMenuOpen = articlesWithOpenMenu.contains(this.id)
    )
}