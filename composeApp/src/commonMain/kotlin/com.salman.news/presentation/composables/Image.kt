package com.salman.news.presentation.composables

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.FilledTonalIconButton
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.unit.dp

/**
 * Created by Muhammed Salman email(mahmadslman@gmail.com) on 1/28/2024.
 */

@Composable
fun TonalIconButton(
    modifier: Modifier = Modifier,
    painter: Painter,
    tint: Color = MaterialTheme.colorScheme.primary,
    contentDescription: String? = null,
    onClick: () -> Unit = {},
) {
    val containerColor = MaterialTheme.colorScheme.primary.copy(alpha = 0.15f)
    FilledTonalIconButton(
        modifier = modifier,
        onClick = onClick,
        colors = IconButtonDefaults.filledTonalIconButtonColors(containerColor = containerColor),
        shape = MaterialTheme.shapes.large,
    ) {
        Icon(painter, contentDescription, tint = tint)
    }
}

@Composable
fun DefaultIcon(
    modifier: Modifier = Modifier,
    painter: Painter,
    contentDescription: String? = null,
    tint: Color = MaterialTheme.colorScheme.primary,
    containerColor: Color = Color.Transparent,
    contentPadding: PaddingValues = PaddingValues(8.dp),
    onClick: (() -> Unit)? = null
) {
    Box(
        modifier = Modifier
            .clip(MaterialTheme.shapes.large)
            .background(containerColor)
            .then(if (onClick != null) Modifier.clickable(onClick = onClick) else Modifier)
            .padding(contentPadding)
            .then(modifier),
        contentAlignment = Alignment.Center
    ) {
        Icon(painter, contentDescription, tint = tint)
    }
}