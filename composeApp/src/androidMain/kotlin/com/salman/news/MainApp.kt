package com.salman.news

import android.app.Application
import com.salman.news.di.dataModule
import com.salman.news.presentation.sharedModule
import org.koin.core.context.startKoin

/**
 * Created by Muhammed Salman email(mahmadslman@gmail.com) on 1/25/2024.
 */
class MainApp : Application() {

    override fun onCreate() {
        super.onCreate()
        startKoin {
            modules(sharedModule + dataModule)
        }
    }
}