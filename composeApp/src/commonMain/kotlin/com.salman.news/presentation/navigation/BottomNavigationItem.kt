package com.salman.news.presentation.navigation

import androidx.compose.animation.animateContentSize
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.unit.dp
import cafe.adriel.voyager.navigator.tab.LocalTabNavigator
import com.salman.news.presentation.theme.LightGray

/**
 * Created by Muhammed Salman email(mahmadslman@gmail.com) on 1/27/2024.
 */

@Composable
fun BottomNavigationItem(tab: NavigationTab) {
    val tabNavigator = LocalTabNavigator.current
    Column(
        modifier = Modifier
            .clip(MaterialTheme.shapes.small)
            .clickable { tabNavigator.current = tab }
            .padding(8.dp)
            .animateContentSize(),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        val tint = if (tab.isCurrentScreen()) {
            MaterialTheme.colorScheme.primary
        } else {
            LightGray
        }
        Icon(
            painter = tab.options.icon!!,
            contentDescription = tab.options.title,
            tint = tint,
        )
        Spacer(Modifier.height(4.dp))
        Text(
            text = tab.options.title,
            style = MaterialTheme.typography.bodyMedium,
            color = tint
        )
    }
}