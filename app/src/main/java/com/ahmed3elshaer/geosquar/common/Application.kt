/*
 * *
 *  * Created by Ahmed Elshaer on 10/26/19 4:17 AM
 *  * Copyright (c) 2019 . All rights reserved.
 *  * Last modified 10/24/19 8:46 PM
 *
 */

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
