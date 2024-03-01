package com.salman.news.presentation.composables

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.RowScope
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.salman.news.MR
import com.salman.news.presentation.theme.Dimens
import dev.icerock.moko.resources.compose.painterResource

/**
 * Created by Muhammed Salman email(mahmadslman@gmail.com) on 2/8/2024.
 */
@Composable
fun ScreenWithTopBar(
    modifier: Modifier = Modifier,
    contentPadding: PaddingValues = PaddingValues(
        horizontal = 16.dp,
        vertical = 32.dp
    ),
    title: String,
    onBackPressed: () -> Unit = {},
    actions: @Composable RowScope.() -> Unit = {},
    content: @Composable () -> Unit,
) {
    Column(
        modifier = modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.background),
    ) {
        Row(
            modifier = Modifier.fillMaxWidth()
                .padding(Dimens.ScreenPadding)
        ) {
            TonalIconButton(
                painter = painterResource(MR.images.baseline_arrow_back_24),
                onClick = onBackPressed
            )
            Spacer(Modifier.width(16.dp))
            Text(
                title,
                style = MaterialTheme.typography.titleLarge,
                color = MaterialTheme.colorScheme.primary
            )
            Spacer(Modifier.weight(1f))
            Row(horizontalArrangement = Arrangement.End) {
                actions()
            }
        }
        Box(Modifier.padding(contentPadding)) {
            content()
        }
    }
}