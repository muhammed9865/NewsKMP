package com.salman.news.presentation.theme

import androidx.compose.material3.Typography
import androidx.compose.runtime.Composable
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.unit.sp
import com.salman.news.MR
import dev.icerock.moko.resources.compose.fontFamilyResource

/**
 * Created by Muhammed Salman email(mahmadslman@gmail.com) on 1/28/2024.
 */

@Composable
fun Typography() = run {
    val jomhuriaFontFamily = fontFamilyResource(MR.fonts.Jomhuria.regular)
    val jomolhariFontFamily = fontFamilyResource(MR.fonts.Jomolhari.regular)

    Typography(
        titleLarge = TextStyle(
            fontFamily = jomhuriaFontFamily,
            fontSize = 32.sp
        ),
        titleMedium = TextStyle(
            fontFamily = jomhuriaFontFamily,
            fontSize = 28.sp
        ),
        titleSmall = TextStyle(
            fontFamily = jomhuriaFontFamily,
            fontSize = 22.sp
        ),
        bodyLarge = TextStyle(
            fontFamily = jomolhariFontFamily,
            fontSize = 14.sp
        ),
        bodyMedium = TextStyle(
            fontFamily = jomolhariFontFamily,
            fontSize = 10.sp
        ),
        bodySmall = TextStyle(
            fontFamily = jomolhariFontFamily,
            fontSize = 8.sp
        ),
        labelMedium = TextStyle(
            fontFamily = jomhuriaFontFamily,
            fontSize = 16.sp
        ),
        labelLarge = TextStyle(
            fontFamily = jomhuriaFontFamily,
            fontSize = 18.sp
        )
    )
}