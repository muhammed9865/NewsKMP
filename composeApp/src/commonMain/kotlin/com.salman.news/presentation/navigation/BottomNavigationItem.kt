package com.salman.news.presentation.navigation

import androidx.compose.animation.animateContentSize
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.unit.dp
import cafe.adriel.voyager.navigator.tab.LocalTabNavigator

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
        val tint = MaterialTheme.colorScheme.primary
        Icon(
            painter = tab.options.icon!!,
            contentDescription = tab.options.title,
            tint = tint,
        )
        Spacer(Modifier.height(4.dp))
        if (tab.isCurrentScreen()) {
            Box(
                Modifier.size(6.dp)
                    .clip(CircleShape)
                    .background(tint)
            )
        }
    }
}