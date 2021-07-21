package com.titou.blaum

import android.app.Application
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.startKoin


class Application : Application() {

    override fun onCreate() {
        super.onCreate()

        val koinApplication = startKoin {
            androidContext(this@Application)
            modules(KoinModules.toList())
        }

    }
}
