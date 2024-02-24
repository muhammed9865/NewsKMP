package com.salman.news.data.di

import com.salman.news.domain.usecases.*
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
    factory { SendFeedbackUseCase(get()) }
    factory { ValidateFeedbackInputUseCase() }
    factory { GetScreenModeFlowUseCase(get()) }
    factory { SetScreenModeUseCase(get()) }
    factory { GetBlockedSourcesFlowUseCase(get()) }
    factory { GetBlockedAuthorsFlowUseCase(get()) }
    factory { RemoveItemFromBlockListUseCase(get()) }
    factory { GetAllSourcesUseCase(get()) }
    factory { GetAllAuthorsUseCase(get()) }
    factory { BlockSourceUseCase(get()) }
    factory { BlockAuthorUseCase(get()) }
    factory { ClearBlackListUseCase(get()) }
    factory { SearchByQueryUseCase(get()) }
}