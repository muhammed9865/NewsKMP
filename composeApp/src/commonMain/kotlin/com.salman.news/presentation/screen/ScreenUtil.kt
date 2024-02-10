package com.salman.news.presentation.screen

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.ReadOnlyComposable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import com.salman.news.presentation.theme.Dimens

/**
 * Created by Muhammed Salman email(mahmadslman@gmail.com) on 1/25/2024.
 */
val ScreenModifier
    @Composable
    @ReadOnlyComposable
    get() = Modifier
        .fillMaxSize()
        .background(MaterialTheme.colorScheme.background)
        .padding(Dimens.ScreenPadding)

@Composable
fun Modifier.dialogScreenModifier() =
    fillMaxWidth()
        .clip(MaterialTheme.shapes.medium)
        .background(MaterialTheme.colorScheme.background)
        .padding(Dimens.ScreenPadding)
