package com.salman.news.presentation.composables

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.core.tween
import androidx.compose.animation.slideInVertically
import androidx.compose.animation.slideOutVertically
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.IntOffset
import com.salman.news.presentation.theme.Dimens

@Composable
fun ContainerWithError(
    modifier: Modifier = Modifier,
    error: String? = null,
    content: @Composable () -> Unit,
) {
    // save the error state so that it doesn't change while the composable is recomposed
    val animationDurationMs = 200
    val animationSpec = tween<IntOffset>(
        animationDurationMs,
        easing = { fraction -> fraction }
    )
    var errorState by remember { mutableStateOf("") }
    if (error != null) {
        errorState = error
    }
    Box(modifier = modifier) {
        content()
        AnimatedVisibility(
            visible = !error.isNullOrBlank(),
            modifier = Modifier.fillMaxWidth(),
            enter = slideInVertically(animationSpec = animationSpec),
            exit = slideOutVertically(animationSpec = animationSpec)
        ) {
            Text(
                text = errorState,
                style = MaterialTheme.typography.bodyLarge,
                color = MaterialTheme.colorScheme.onErrorContainer,
                modifier = Modifier
                    .background(MaterialTheme.colorScheme.errorContainer)
                    .padding(Dimens.ItemsPadding),
                textAlign = TextAlign.Center
            )
        }
    }
}