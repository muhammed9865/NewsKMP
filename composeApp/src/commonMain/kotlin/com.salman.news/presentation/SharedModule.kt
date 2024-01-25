package com.salman.news.presentation

import com.salman.news.presentation.screen.home.HomeViewModel
import org.koin.dsl.module

/**
 * Created by Muhammed Salman email(mahmadslman@gmail.com) on 1/25/2024.
 */

interface Shit {
    fun print(text: String)
}

class ShitImpl : Shit {
    override fun print(text: String) {
        println(text)
    }
}
val sharedModule = module {
    single<Shit> { ShitImpl() }
    factory {
        HomeViewModel(get())
    }
}