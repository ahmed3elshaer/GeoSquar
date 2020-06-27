/*
 * *
 *  * Created by Ahmed Elshaer on 6/20/20 1:10 PM
 *  * Copyright (c) 2020 . All rights reserved.
 *  * Last modified 6/20/20 1:10 PM
 *
 */
package com.ahmed3elshaer.geosquar.common.di

import com.ahmed3elshaer.geosquar.common.loader.FourSquareGlideAppModule
import com.ahmed3elshaer.geosquar.home.MainActivity
import dagger.Component
import javax.inject.Singleton

@Singleton
@Component(modules = [MainModule::class])
interface MainComponent {
    fun inject(mainActivity: MainActivity)

    fun inject(glideAppModule: FourSquareGlideAppModule)
}
