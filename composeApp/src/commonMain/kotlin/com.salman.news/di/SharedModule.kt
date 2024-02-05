package com.salman.news.di

import com.salman.news.data.di.sharedDataModule
import com.salman.news.data.di.useCasesModule
import com.salman.news.presentation.di.viewModelModule
import org.koin.dsl.module

/**
 * Created by Muhammed Salman email(mahmadslman@gmail.com) on 1/31/2024.
 */
val sharedModule = module {
    includes(sharedDataModule, useCasesModule, viewModelModule)
}