package com.fourcore

import android.app.Application
import com.fourcore.di.appModule
import com.fourcore.di.loginModule
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.startKoin

class ExampleApplication: Application() {
    override fun onCreate() {
        super.onCreate()
        startKoin {
            // Android context
            androidContext(this@ExampleApplication)
            // modules
            modules(listOf(appModule, loginModule))
        }
    }
}