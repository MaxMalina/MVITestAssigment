package com.exam.githubbrowser

import android.app.Application
import com.exam.githubbrowser.di.appModules
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidLogger
import org.koin.core.context.startKoin
import org.koin.core.logger.Level

class GithubBrowserApplication: Application() {

    override fun onCreate() {
        super.onCreate()
        startKoin {
            androidLogger(Level.ERROR)
            androidContext(this@GithubBrowserApplication)
            modules(appModules)
        }
    }

}
