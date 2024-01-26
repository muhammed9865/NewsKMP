package com.salman.news.presentation.screen.home

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.material.Button
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.text.intl.Locale
import cafe.adriel.voyager.core.screen.Screen
import cafe.adriel.voyager.koin.getScreenModel
import cafe.adriel.voyager.navigator.LocalNavigator
import cafe.adriel.voyager.navigator.currentOrThrow
import com.salman.news.data.source.remote.ArticlesRemoteDataSource
import com.salman.news.presentation.screen.ScreenModifier
import com.salman.news.presentation.screen.search.SearchScreen

/**
 * Created by Muhammed Salman email(mahmadslman@gmail.com) on 1/25/2024.
 */
class HomeScreen : Screen {
    @Composable
    override fun Content() {
        val viewModel = getScreenModel<HomeViewModel>()
        val navigator = LocalNavigator.currentOrThrow
        val countryCode = Locale.current.region.lowercase()
        LaunchedEffect(Unit) {
            viewModel.print("", countryCode)
        }

        Column(
            modifier = ScreenModifier,
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            Text("Hello From HomeScreen")
            Button(onClick =  { navigator.push(SearchScreen())}) {
                Text("Search")
            }
        }
    }
}