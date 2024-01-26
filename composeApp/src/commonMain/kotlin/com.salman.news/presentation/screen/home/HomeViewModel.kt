package com.salman.news.presentation.screen.home

import cafe.adriel.voyager.core.model.ScreenModel
import cafe.adriel.voyager.core.model.screenModelScope
import com.salman.news.data.source.remote.ArticlesRemoteDataSource
import com.salman.news.presentation.Shit
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.IO
import kotlinx.coroutines.launch

/**
 * Created by Muhammed Salman email(mahmadslman@gmail.com) on 1/25/2024.
 */
class HomeViewModel(
    private val shit: Shit,
    val source: ArticlesRemoteDataSource
): ScreenModel {


    fun print(text: String, countryCode: String) {
        screenModelScope.launch(Dispatchers.IO) {
            val result = source.getTopHeadlines(1, countryCode)
            shit.print(result.toString())
        }
    }
}