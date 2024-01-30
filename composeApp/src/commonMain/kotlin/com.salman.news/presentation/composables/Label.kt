package com.salman.news.presentation.composables

import androidx.compose.foundation.background
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.text.style.TextAlign

/**
 * Created by Muhammed Salman email(mahmadslman@gmail.com) on 1/30/2024.
 */

@Composable
fun Label(
    modifier: Modifier = Modifier,
    label: String,
) {
    Text(
        modifier = modifier
            .clip(MaterialTheme.shapes.large)
            .background(MaterialTheme.colorScheme.surface.copy(alpha = 0.5f))
           ,
        textAlign = TextAlign.Center,
        text = label,
        style = MaterialTheme.typography.labelLarge
    )
}