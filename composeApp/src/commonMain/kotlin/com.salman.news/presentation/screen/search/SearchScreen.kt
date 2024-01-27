package com.salman.news.presentation.screen.search

import androidx.compose.foundation.layout.Column
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import cafe.adriel.voyager.core.screen.Screen
import com.salman.news.MR
import com.salman.news.presentation.screen.ScreenModifier

/**
 * Created by Muhammed Salman email(mahmadslman@gmail.com) on 1/25/2024.
 */
class SearchScreen : Screen {
    @Composable
    override fun Content() {
        Column(ScreenModifier) {
            Text("Hello FRom Search, this is your feed")
        }
    }
}