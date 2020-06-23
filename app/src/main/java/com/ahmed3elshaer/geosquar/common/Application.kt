/*
 * *
 *  * Created by Ahmed Elshaer on 10/26/19 4:17 AM
 *  * Copyright (c) 2019 . All rights reserved.
 *  * Last modified 10/24/19 8:46 PM
 *
 */

package com.ahmed3elshaer.geosquar.common

import androidx.multidex.MultiDexApplication
import com.ahmed3elshaer.geosquar.common.di.DaggerMainComponent
import com.ahmed3elshaer.geosquar.common.di.MainComponent
import com.ahmed3elshaer.geosquar.common.di.MainModule

class Application : MultiDexApplication() {
    private var daggerMainComponent: MainComponent? = null

    override fun onCreate() {
        super.onCreate()
        daggerMainComponent =
            DaggerMainComponent.builder().applicationContext(applicationContext).build()
    }


    fun getMainComponent() = if (daggerMainComponent != null)
        daggerMainComponent
    else null

}
