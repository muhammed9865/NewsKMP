package com.salman.news.data.di

import com.salman.news.domain.usecases.GetArticlesFlowUseCase
import com.salman.news.domain.usecases.GetBookmarkedArticlesFlowUseCase
import com.salman.news.domain.usecases.SendIssueUseCase
import com.salman.news.domain.usecases.ToggleArticleBookmarkUseCase
import com.salman.news.domain.usecases.ValidateIssueInputUseCase
import org.koin.dsl.module

/**
 * Created by Muhammed Salman email(mahmadslman@gmail.com) on 2/5/2024.
 */
val useCasesModule = module {
    factory { GetBookmarkedArticlesFlowUseCase(get()) }
    factory { GetArticlesFlowUseCase(get()) }
    factory { ToggleArticleBookmarkUseCase(get()) }
    factory { ValidateIssueInputUseCase() }
    factory { SendIssueUseCase(get()) }
}