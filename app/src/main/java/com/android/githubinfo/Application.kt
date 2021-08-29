package com.android.githubinfo

import android.app.Application
import com.android.githubinfo.di.apiModule
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.startKoin

class Application : Application() {

    override fun onCreate() {
        super.onCreate()
        // start Koin!
        startKoin {
            // declare used Android context
            androidContext(this@Application)
            // declare modules
            modules(listOf(apiModule))
        }
    }
}