package com.salman.news.presentation.composables

import androidx.compose.material3.FilledTonalIconButton
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.painter.Painter
import com.salman.news.presentation.theme.MetabolicBlue20A

/**
 * Created by Muhammed Salman email(mahmadslman@gmail.com) on 1/28/2024.
 */

@Composable
fun TonalIcon(
    modifier: Modifier = Modifier,
    painter: Painter,
    tint: Color = MaterialTheme.colorScheme.primary,
    contentDescription: String? = null,
    onClick: () -> Unit = {},
) {
    FilledTonalIconButton(
        modifier = modifier,
        onClick = onClick,
        colors = IconButtonDefaults.filledTonalIconButtonColors(containerColor = MetabolicBlue20A),
        shape = MaterialTheme.shapes.large,
    ) {
        Icon(painter, contentDescription, tint = tint)
    }
}