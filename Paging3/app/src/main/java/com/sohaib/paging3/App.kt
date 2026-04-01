package com.sohaib.paging3

import android.app.Application
import com.sohaib.paging3.di.KoinModules
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.startKoin

class App : Application() {

    override fun onCreate() {
        super.onCreate()
        startKoin {
            androidContext(this@App)
            modules(KoinModules.modules)
        }
    }
}
