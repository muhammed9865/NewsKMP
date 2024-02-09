package com.salman.news.presentation

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview
import org.koin.android.ext.android.inject

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val appEntryPoint: AppEntryContent by inject()

        setContent {
            appEntryPoint.Content()
        }
    }
}

@Preview
@Composable
fun AppAndroidPreview() {
    AppEntryContent().Content()
}