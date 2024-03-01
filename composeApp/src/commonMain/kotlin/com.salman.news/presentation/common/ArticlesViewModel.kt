package com.salman.news.presentation.common

import androidx.compose.runtime.mutableStateListOf
import com.salman.news.core.CoroutineViewModel
import com.salman.news.core.addOrRemove
import com.salman.news.domain.model.Article
import com.salman.news.domain.usecases.BlockAuthorUseCase
import com.salman.news.domain.usecases.BlockSourceUseCase
import com.salman.news.domain.usecases.ToggleArticleBookmarkUseCase
import com.salman.news.presentation.model.ArticleUI

/**
 * Created by Muhammed Salman email(mahmadslman@gmail.com) on 2/24/2024.
 */
abstract class ArticlesViewModel(
    private val blockAuthorUseCase: BlockAuthorUseCase,
    private val blockSourceUseCase: BlockSourceUseCase,
    private val toggleArticleBookmarkUseCase: ToggleArticleBookmarkUseCase,
) : CoroutineViewModel() {

    protected var articleIDsWithMenuOpen = hashSetOf<Long>()
    val articles by lazy {
        mutableStateListOf<ArticleUI>()
    }


    fun toggleArticleBookmark(articleUI: ArticleUI) {
        scope.launchIO {
            toggleArticleBookmarkUseCase(articleUI)
        }
    }

    fun muteAuthor(article: ArticleUI) {
        scope.launchIO {
            blockAuthorUseCase(article.article.author)
        }
    }

    fun muteSource(article: ArticleUI) {
        scope.launchIO {
            blockSourceUseCase(article.article.source)
        }
    }

    fun toggleArticleOptionsMenu(index: Int) {
        val article = articles[index]
        val isOpen = article.isOptionsMenuOpen
        articles[index] = article.copy(isOptionsMenuOpen = !isOpen)
        articleIDsWithMenuOpen.addOrRemove(article.article.id)
    }

    protected fun Article.toUI(articlesWithOpenMenu: HashSet<Long>) = ArticleUI(
        article = this,
        isOptionsMenuOpen = articlesWithOpenMenu.contains(this.id)
    )
}