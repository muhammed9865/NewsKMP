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
    val nunitoRegular = fontFamilyResource(MR.fonts.Nunito.regular)
    val nunitoMedium = fontFamilyResource(MR.fonts.Nunito.medium)
    val nunitoLight = fontFamilyResource(MR.fonts.Nunito.light)

    Typography(
        titleLarge = TextStyle(
            fontFamily = jomhuriaFontFamily,
            fontSize = 32.sp
        ),
        titleMedium = TextStyle(
            fontFamily = nunitoMedium,
            fontSize = 16.sp
        ),
        titleSmall = TextStyle(
            fontFamily = nunitoMedium,
            fontSize = 14.sp
        ),
        bodyLarge = TextStyle(
            fontFamily = nunitoRegular,
            fontSize = 14.sp
        ),
        bodyMedium = TextStyle(
            fontFamily = nunitoRegular,
            fontSize = 10.sp
        ),
        bodySmall = TextStyle(
            fontFamily = nunitoRegular,
            fontSize = 8.sp
        ),
        labelSmall = TextStyle(
            fontFamily = nunitoLight,
            fontSize = 10.sp
        ),
        labelMedium = TextStyle(
            fontFamily = nunitoRegular,
            fontSize = 16.sp
        ),
        labelLarge = TextStyle(
            fontFamily = jomhuriaFontFamily,
            fontSize = 18.sp
        )
    )
}