/*
 * *
 *  * Created by Ahmed Elshaer on 24/06/20 04:09
 *  * Copyright (c) 2020 . All rights reserved.
 *  * Last modified 23/06/20 22:43
 *
 */

package com.ahmed3elshaer.geosquar.common.di

import android.content.Context
import android.content.SharedPreferences
import com.ahmed3elshaer.geosquar.common.*
import com.ahmed3elshaer.geosquar.common.local.VenuesDao
import com.ahmed3elshaer.geosquar.common.local.VenuesDatabase
import com.ahmed3elshaer.geosquar.common.schedulers.BaseSchedulerProvider
import com.ahmed3elshaer.geosquar.common.schedulers.SchedulerProvider
import com.ahmed3elshaer.geosquar.home.HomeViewModel
import com.ahmed3elshaer.geosquar.home.usecases.ExploreVenuesCacheUseCase
import com.ahmed3elshaer.geosquar.home.usecases.ExploreVenuesRealtimeUseCase
import com.ahmed3elshaer.geosquar.home.usecases.ExploreVenuesSingleUseCase
import com.squareup.moshi.Moshi
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import dagger.Module
import dagger.Provides
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.moshi.MoshiConverterFactory
import java.util.concurrent.TimeUnit
import javax.inject.Singleton

@Module
class MainModule(private val context: Context) {

    @Singleton
    @Provides
    fun provideUrl(): String = "https://api.foursquare.com/v2/"

    @Singleton
    @Provides
    fun provideMoshiBuilder(): Moshi = Moshi.Builder().add(KotlinJsonAdapterFactory()).build()

    @Singleton
    @Provides
    fun provideOkHttpClient(): OkHttpClient {
        val logging = HttpLoggingInterceptor()
        logging.level = HttpLoggingInterceptor.Level.BODY
        return OkHttpClient.Builder()
            .connectTimeout(2, TimeUnit.MINUTES)
            .readTimeout(2, TimeUnit.MINUTES)
            .addInterceptor(logging)
            .addInterceptor(AuthInterceptor())
            .build()
    }

    @Singleton
    @Provides
    fun provideSharedPreference(): SharedPreferences =
        context.getSharedPreferences("GeoSquare", Context.MODE_PRIVATE)

    @Singleton
    @Provides
    fun provideSharedPreferenceWrapper(sharedPreferences: SharedPreferences): SharedPrefWrapper =
        SharedPrefWrapper(sharedPreferences)

    @Singleton
    @Provides
    fun provideVenuesDatabase(): VenuesDao = VenuesDatabase.getInstance(context).moviesDao()

    @Singleton
    @Provides
    fun createWebService(
        okHttpClient: OkHttpClient,
        moshi: Moshi,
        url: String
    ): FourSquareApi {
        val retrofit = Retrofit.Builder()
            .baseUrl(url)
            .client(okHttpClient)
            .addConverterFactory(MoshiConverterFactory.create(moshi))
            .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
            .build()
        return retrofit.create(FourSquareApi::class.java)
    }

    @Singleton
    @Provides
    fun provideSchedulerProvider(): BaseSchedulerProvider = SchedulerProvider()

    @Provides
    fun provideRepository(
        sharedPrefWrapper: SharedPrefWrapper,
        fourSquareApi: FourSquareApi,
        venuesDao: VenuesDao
    ): Repository = Repository(sharedPrefWrapper, fourSquareApi, venuesDao)

    @Provides
    fun provideVenuesCacheUseCase(
        repository: Repository
    ): ExploreVenuesCacheUseCase = ExploreVenuesCacheUseCase(repository)

    @Provides
    fun provideVenuesRealtimeUseCase(
        repository: Repository,
        schedulerProvider: BaseSchedulerProvider
    ): ExploreVenuesRealtimeUseCase = ExploreVenuesRealtimeUseCase(repository, schedulerProvider)

    @Provides
    fun provideVenuesSingleUseCase(
        repository: Repository,
        schedulerProvider: BaseSchedulerProvider
    ): ExploreVenuesSingleUseCase = ExploreVenuesSingleUseCase(repository, schedulerProvider)


    @Provides
    fun provideHomeViewModel(
        schedulerProvider: BaseSchedulerProvider,
        exploreVenuesRealtimeUseCase: ExploreVenuesRealtimeUseCase,
        exploreVenuesSingleUseCase: ExploreVenuesSingleUseCase,
        exploreVenuesCacheUseCase: ExploreVenuesCacheUseCase
    ): HomeViewModel = HomeViewModel(
        schedulerProvider,
        exploreVenuesRealtimeUseCase,
        exploreVenuesSingleUseCase,
        exploreVenuesCacheUseCase
    )

}