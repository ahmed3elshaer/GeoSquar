/*
 * *
 *  * Created by Ahmed Elshaer on 6/20/20 1:10 PM
 *  * Copyright (c) 2020 . All rights reserved.
 *  * Last modified 6/20/20 1:10 PM
 *
 */
package com.ahmed3elshaer.geosquar.common.di

import android.content.Context
import com.ahmed3elshaer.geosquar.common.Repository
import com.ahmed3elshaer.geosquar.common.SharedPrefWrapper
import com.ahmed3elshaer.geosquar.common.loader.FourSquareGlideAppModule
import com.ahmed3elshaer.geosquar.common.schedulers.SchedulerProvider
import com.ahmed3elshaer.geosquar.home.MainActivity
import dagger.BindsInstance
import dagger.Component
import javax.inject.Singleton

@Singleton
@Component(modules = [MainModule::class])
interface MainComponent {

    @Component.Builder
    interface Builder {
        fun build(): MainComponent

        @BindsInstance
        fun applicationContext(context: Context): Builder
    }

    fun inject(glideAppModule: FourSquareGlideAppModule)
    fun provideRepository(): Repository
    fun provideSharedPref(): SharedPrefWrapper
    fun provideScheduler(): SchedulerProvider
}

