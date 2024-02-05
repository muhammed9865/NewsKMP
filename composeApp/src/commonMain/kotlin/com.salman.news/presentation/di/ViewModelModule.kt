package com.salman.news.presentation.di

import com.salman.news.presentation.screen.bookmark.BookmarkViewModel
import com.salman.news.presentation.screen.home.HomeViewModel
import org.koin.dsl.module

/**
 * Created by Muhammed Salman email(mahmadslman@gmail.com) on 1/31/2024.
 */

val viewModelModule = module {
    factory { HomeViewModel(get()) }
    factory { BookmarkViewModel(get(), get(), get()) }
}