/*
 * *
 *  * Created by Ahmed Elshaer on 24/06/20 04:05
 *  * Copyright (c) 2020 . All rights reserved.
 *  * Last modified 23/06/20 22:43
 *
 */

package com.ahmed3elshaer.geosquar.common.di

import com.ahmed3elshaer.geosquar.common.loader.FourSquareGlideAppModule
import com.ahmed3elshaer.geosquar.home.MainActivity
import com.bumptech.glide.module.AppGlideModule
import dagger.Component
import javax.inject.Singleton

@Singleton
@Component(modules = [MainModule::class])
interface MainComponent {
    fun poke(mainActivity: MainActivity)
    fun poke(FourSquareGlideAppModule: FourSquareGlideAppModule)
}