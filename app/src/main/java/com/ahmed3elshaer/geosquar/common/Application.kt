package com.ahmed3elshaer.geosquar.common

import androidx.multidex.MultiDexApplication
import org.koin.android.ext.android.startKoin

class Application : MultiDexApplication() {
    override fun onCreate() {
        super.onCreate()
        startKoin(
            this,
            listOf(applicationModules),
            loadPropertiesFromFile = true
        )

    }
}