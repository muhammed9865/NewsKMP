package com.salman.news.presentation.composables

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import com.salman.news.presentation.theme.Dimens

/**
 * Created by Muhammed Salman email(mahmadslman@gmail.com) on 1/30/2024.
 */

@Composable
fun Label(
    modifier: Modifier = Modifier,
    label: String,
    maxLines: Int = Int.MAX_VALUE
) {
    Text(
        modifier = modifier
            .clip(MaterialTheme.shapes.large)
            .background(MaterialTheme.colorScheme.surface.copy(alpha = 0.5f))
            .padding(Dimens.LabelContentPadding),
        textAlign = TextAlign.Center,
        text = label,
        style = MaterialTheme.typography.labelLarge,
        maxLines = maxLines,
        overflow = TextOverflow.Ellipsis
    )
}