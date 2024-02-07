package com.salman.news.presentation.screen.more

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import cafe.adriel.voyager.core.screen.Screen
import com.salman.news.MR
import com.salman.news.presentation.LocalTopNavigator
import com.salman.news.presentation.screen.ScreenModifier
import dev.icerock.moko.resources.compose.stringResource

/**
 * Created by Muhammed Salman email(mahmadslman@gmail.com) on 1/27/2024.
 */
class MoreScreen : Screen {
    @Composable
    override fun Content() {
        val navigator = LocalTopNavigator.current
        Column(
            ScreenModifier,
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            Header(header = stringResource(MR.strings.settings))
            SettingScreen.settings().forEach {
                SettingScreen(setting = it) {
                    navigator.push(it.destination())
                }
            }
        }
    }

    @Composable
    private fun Header(modifier: Modifier = Modifier, header: String) {
        Text(
            modifier = modifier,
            text = header,
            style = MaterialTheme.typography.titleMedium,
            color = MaterialTheme.colorScheme.onBackground
        )
    }

    @Composable
    private fun SettingScreen(
        modifier: Modifier = Modifier,
        setting: SettingScreen,
        onNavigate: () -> Unit
    ) {
        val tint = MaterialTheme.colorScheme.primary.copy(alpha = 0.8f)
        Row(
            modifier = modifier
                .fillMaxWidth()
                .clickable(onClick = onNavigate)
                .padding(vertical = 4.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Icon(painter = setting.icon(), tint = tint, contentDescription = setting.title())
            Spacer(Modifier.width(12.dp))
            Text(text = setting.title(), style = MaterialTheme.typography.bodyLarge)
        }
    }
}