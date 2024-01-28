package com.salman.news.presentation.theme

import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color

/**
 * Created by Muhammed Salman email(mahmadslman@gmail.com) on 1/27/2024.
 */
val DarkColorScheme = darkColorScheme(
    primary = MetabolicBlue,
    onPrimary = Color.White,
    background = Color.White,
    onBackground = MetabolicBlue,
    primaryContainer = QuickSilver,
    onPrimaryContainer = CharcoalBlue,
)

val LightColorScheme = lightColorScheme(
    primary = MetabolicBlue,
    onPrimary = Color.White,
    background = Color.White,
    onBackground = MetabolicBlue,
    primaryContainer = QuickSilver,
    onPrimaryContainer = CharcoalBlue,
    surface = AzuerishWhite,
    onSurface = MetabolicBlue
)

@Composable
expect fun NewsTheme(
    darkTheme: Boolean,
    dynamicColor: Boolean,
    content: @Composable () -> Unit,
)