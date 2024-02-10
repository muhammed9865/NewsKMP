package com.salman.news.presentation.composables

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.window.Dialog
import androidx.compose.ui.window.DialogProperties
import com.salman.news.MR
import com.salman.news.presentation.screen.dialogScreenModifier
import com.salman.news.presentation.theme.Dimens
import dev.icerock.moko.resources.compose.painterResource
import kotlinx.coroutines.delay
import kotlin.time.Duration
import kotlin.time.Duration.Companion.milliseconds

/**
 * Created by Muhammed Salman email(mahmadslman@gmail.com) on 2/7/2024.
 */
@Composable
fun SuccessDialog(
    modifier: Modifier = Modifier,
    text: String,
    icon: Painter? = null,
    dismissAfterMs: Duration = 2000.milliseconds,
    onDismiss: () -> Unit = {},
) {
    LaunchedEffect(Unit) {
        delay(dismissAfterMs)
        onDismiss()
    }
    Dialog(
        properties = DialogProperties(
            dismissOnBackPress = false,
            dismissOnClickOutside = false,
        ),
        onDismissRequest = { }
    ) {
        Column(
            modifier = modifier.dialogScreenModifier(),
            verticalArrangement = Arrangement.spacedBy(Dimens.ItemsPadding),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Image(
                painter = painterResource(MR.images.ic_sitting_reading),
                contentDescription = "Success",
            )
            Row(
                verticalAlignment = Alignment.CenterVertically
            ) {
                icon?.let {
                    Icon(
                        painter = icon,
                        contentDescription = text,
                    )
                }
                Text(
                    text = text,
                    style = MaterialTheme.typography.titleLarge,
                    color = MaterialTheme.colorScheme.primary,
                    textAlign = TextAlign.Center,
                )
            }
        }
    }
}