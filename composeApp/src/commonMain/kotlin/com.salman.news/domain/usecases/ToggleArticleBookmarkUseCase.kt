package com.salman.news.domain.usecases

import com.salman.news.domain.repository.ArticleRepository
import com.salman.news.presentation.model.ArticleUI
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.IO
import kotlinx.coroutines.withContext

/**
 * Created by Muhammed Salman email(mahmadslman@gmail.com) on 2/5/2024.
 */
class ToggleArticleBookmarkUseCase(
    private val articleRepository: ArticleRepository
) {
    suspend operator fun invoke(articleUI: ArticleUI) {
        withContext(Dispatchers.IO) {
            articleRepository.toggleArticleBookmark(articleUI.article.id)
        }
    }
}