package com.salman.news.presentation.di

import com.salman.news.presentation.screen.blocklist.BlockListViewModel
import com.salman.news.presentation.screen.bookmark.BookmarkViewModel
import com.salman.news.presentation.screen.feedback.FeedbackViewModel
import com.salman.news.presentation.screen.home.HomeViewModel
import com.salman.news.presentation.screen.issue.SendIssueViewModel
import com.salman.news.presentation.screen.mode.ScreenModeViewModel
import com.salman.news.presentation.screen.more.MoreViewModel
import com.salman.news.presentation.screen.search.SearchViewModel
import org.koin.dsl.module

/**
 * Created by Muhammed Salman email(mahmadslman@gmail.com) on 1/31/2024.
 */

val viewModelModule = module {
    factory { HomeViewModel(get(), get(), get(), get(), get()) }
    factory { BookmarkViewModel(get(), get(), get(), get()) }
    factory { SendIssueViewModel(get(), get()) }
    factory { MoreViewModel() }
    factory { FeedbackViewModel(get(), get()) }
    factory { ScreenModeViewModel(get(), get()) }
    factory { BlockListViewModel(get(), get(), get(), get(), get(), get(), get(), get()) }
    factory { SearchViewModel(get()) }
}