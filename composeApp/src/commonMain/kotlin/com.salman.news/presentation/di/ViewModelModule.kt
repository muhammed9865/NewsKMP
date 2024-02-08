package com.salman.news.presentation.di

import com.salman.news.presentation.screen.bookmark.BookmarkViewModel
import com.salman.news.presentation.screen.home.HomeViewModel
import com.salman.news.presentation.screen.issue.SendIssueViewModel
import com.salman.news.presentation.screen.more.MoreViewModel
import org.koin.dsl.module

/**
 * Created by Muhammed Salman email(mahmadslman@gmail.com) on 1/31/2024.
 */

val viewModelModule = module {
    factory { HomeViewModel(get(), get(), get()) }
    factory { BookmarkViewModel(get(), get(), get()) }
    factory { SendIssueViewModel(get(), get()) }
    factory { MoreViewModel() }
}