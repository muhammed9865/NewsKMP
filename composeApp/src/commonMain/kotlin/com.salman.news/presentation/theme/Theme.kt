package com.salman.news.presentation.theme

import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color

/**
 * Created by Muhammed Salman email(mahmadslman@gmail.com) on 1/27/2024.
 */
val DarkColorScheme = darkColorScheme(
    primary = Color(0xFF64B5F6), // A shade of blue
    onPrimary = Color.White,
    background = Color(0xFF212121), // Dark background
    onBackground = Color.White, // Light text on dark background
    primaryContainer = Color(0xFF455A64), // Dark gray
    onPrimaryContainer = Color.White, // Light text on dark gray
    surface = Color(0xFF303030), // Dark surface color
    onSurface = Color.White, // Light text on dark surface
)

val LightColorScheme = lightColorScheme(
    primary = MetabolicBlue,
    onPrimary = Color.White,
    background = Color.White,
    onBackground = MetabolicBlue,
    primaryContainer = Color.White,
    onPrimaryContainer = CharcoalBlue,
    surface = AzuerishWhite,
    onSurface = MetabolicBlue,
)

@Composable
expect fun NewsTheme(
    darkTheme: Boolean,
    dynamicColor: Boolean,
    content: @Composable () -> Unit,
)