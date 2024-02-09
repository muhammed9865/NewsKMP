package com.salman.news.presentation.composables

import androidx.compose.foundation.layout.*
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.salman.news.MR
import com.salman.news.presentation.screen.ScreenModifier
import dev.icerock.moko.resources.compose.painterResource

/**
 * Created by Muhammed Salman email(mahmadslman@gmail.com) on 2/8/2024.
 */
@Composable
fun ScreenWithNavigationButton(
    modifier: Modifier = Modifier,
    title: String,
    onBackPressed: () -> Unit = {},
    content: @Composable () -> Unit,
) {
    Column(
        modifier = ScreenModifier.then(modifier),
    ) {
        Row(modifier = Modifier.fillMaxWidth()) {
            TonalIconButton(painter = painterResource(MR.images.baseline_arrow_back_24), onClick = onBackPressed)
            Spacer(Modifier.width(8.dp))
            Text(title, style = MaterialTheme.typography.titleLarge)
        }
        Spacer(Modifier.height(32.dp))
        content()
    }
}