/*
 * *
 *  * Created by Ahmed Elshaer on 10/26/19 4:17 AM
 *  * Copyright (c) 2019 . All rights reserved.
 *  * Last modified 10/26/19 3:34 AM
 *
 */

package com.ahmed3elshaer.geosquar.common

import android.content.Context
import com.ahmed3elshaer.geosquar.common.local.VenuesDatabase
import com.ahmed3elshaer.geosquar.common.schedulers.BaseSchedulerProvider
import com.ahmed3elshaer.geosquar.common.schedulers.SchedulerProvider
import com.ahmed3elshaer.geosquar.home.HomeViewModel
import com.ahmed3elshaer.geosquar.home.usecases.ExploreVenuesCacheUseCase
import com.ahmed3elshaer.geosquar.home.usecases.ExploreVenuesRealtimeUseCase
import com.ahmed3elshaer.geosquar.home.usecases.ExploreVenuesSingleUseCase
import com.squareup.moshi.Moshi
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import org.koin.android.ext.koin.androidApplication
import org.koin.android.ext.koin.androidContext
import org.koin.android.viewmodel.ext.koin.viewModel
import org.koin.dsl.module.module
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.moshi.MoshiConverterFactory
import java.util.concurrent.TimeUnit

val applicationModules = module {

    single { Moshi.Builder().add(KotlinJsonAdapterFactory()).build() }

    single { createOkHttpClient() }
    single { androidApplication().getSharedPreferences("GeoSquare", Context.MODE_PRIVATE) }
    single { SharedPrefWrapper(get()) }
    single { VenuesDatabase.getInstance(androidContext()).moviesDao() }
    single<FourSquareApi> {
        createWebService(
            get(),
            get(),
            "https://api.foursquare.com/v2/"
        )

    }
    single { SchedulerProvider() }
    factory { Repository(get(), get(), get()) }

    factory { ExploreVenuesCacheUseCase(get()) }
    factory { ExploreVenuesRealtimeUseCase(get(),get()) }
    factory { ExploreVenuesSingleUseCase(get(),get()) }

    viewModel { HomeViewModel(get(), get(), get(), get()) }
}

fun createOkHttpClient(): OkHttpClient {
    val logging = HttpLoggingInterceptor()
    logging.level = HttpLoggingInterceptor.Level.BODY
    return OkHttpClient.Builder()
        .connectTimeout(2, TimeUnit.MINUTES)
        .readTimeout(2, TimeUnit.MINUTES)
        .addInterceptor(logging)
        .addInterceptor(AuthInterceptor())
        .build()
}


inline fun <reified T> createWebService(
    okHttpClient: OkHttpClient,
    moshi: Moshi,
    url: String
): T {
    val retrofit = Retrofit.Builder()
        .baseUrl(url)
        .client(okHttpClient)
        .addConverterFactory(MoshiConverterFactory.create(moshi))
        .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
        .build()
    return retrofit.create(T::class.java)
}


