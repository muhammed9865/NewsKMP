package com.salman.news.presentation.screen.search_result

import com.salman.news.domain.usecases.BlockAuthorUseCase
import com.salman.news.domain.usecases.BlockSourceUseCase
import com.salman.news.domain.usecases.GetLastSearchResultUseCase
import com.salman.news.domain.usecases.ToggleArticleBookmarkUseCase
import com.salman.news.presentation.common.ArticlesViewModel
import kotlinx.coroutines.flow.map

/**
 * Created by Muhammed Salman email(mahmadslman@gmail.com) on 2/24/2024.
 */
class SearchResultViewModel(
    private val getLastSearchResultUseCase: GetLastSearchResultUseCase,
    blockAuthorUseCase: BlockAuthorUseCase,
    blockSourceUseCase: BlockSourceUseCase,
    toggleArticleBookmarkUseCase: ToggleArticleBookmarkUseCase,
) : ArticlesViewModel(blockAuthorUseCase, blockSourceUseCase, toggleArticleBookmarkUseCase) {

    init {
        scope.launchIO {
            getLastSearchResultUseCase()
                .map { articles ->
                    articles.map { it.toUI(articleIDsWithMenuOpen) }
                }.collect {
                    onMain {
                        articles.addAll(it)
                    }
                }
        }
    }

}
