package com.salman.news

import android.app.Application
import com.salman.news.data.di.PlatformDataModule
import com.salman.news.data.di.sharedDataModule
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.startKoin

/**
 * Created by Muhammed Salman email(mahmadslman@gmail.com) on 1/25/2024.
 */
class MainApp : Application() {

    override fun onCreate() {
        super.onCreate()
        startKoin {
            androidContext(this@MainApp)
            modules(sharedDataModule + PlatformDataModule().module)
        }
    }
}