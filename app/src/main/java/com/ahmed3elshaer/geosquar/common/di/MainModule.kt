/*
 * *
 *  * Created by Ahmed Elshaer on 6/20/20 1:19 PM
 *  * Copyright (c) 2020 . All rights reserved.
 *  * Last modified 6/20/20 1:19 PM
 *
 */

package com.ahmed3elshaer.geosquar.common.di

import android.content.Context
import android.content.SharedPreferences
import com.ahmed3elshaer.geosquar.common.AuthInterceptor
import com.ahmed3elshaer.geosquar.common.FourSquareApi
import com.ahmed3elshaer.geosquar.common.Repository
import com.ahmed3elshaer.geosquar.common.SharedPrefWrapper
import com.ahmed3elshaer.geosquar.common.local.VenuesDao
import com.ahmed3elshaer.geosquar.common.local.VenuesDatabase
import com.ahmed3elshaer.geosquar.common.schedulers.SchedulerProvider
import com.ahmed3elshaer.geosquar.home.HomeViewModelFactory
import com.ahmed3elshaer.geosquar.home.usecases.ExploreVenuesCacheUseCase
import com.ahmed3elshaer.geosquar.home.usecases.ExploreVenuesRealtimeUseCase
import com.ahmed3elshaer.geosquar.home.usecases.ExploreVenuesSingleUseCase
import com.squareup.moshi.Moshi
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import dagger.Module
import dagger.Provides
import java.util.concurrent.TimeUnit
import javax.inject.Singleton
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.moshi.MoshiConverterFactory

@Module
class MainModule(val context: Context) {

    val url = "https://api.foursquare.com/v2/"

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
    fun provideMoshi(): Moshi {
        return Moshi.Builder().add(KotlinJsonAdapterFactory()).build()
    }

    @Singleton
    @Provides
    fun provideSharedPref(): SharedPreferences {
        return context.applicationContext.getSharedPreferences("GeoSquare", Context.MODE_PRIVATE)
    }

    @Singleton
    @Provides
    fun provideSharedPrefWrapper(sharedPreferences: SharedPreferences): SharedPrefWrapper {
        return SharedPrefWrapper(sharedPreferences)
    }

    @Singleton
    @Provides
    fun provideVenuesDao(): VenuesDao {
        return VenuesDatabase.getInstance(context).moviesDao()
    }

    @Singleton
    @Provides
    fun provideApi(okHttpClient: OkHttpClient, moshi: Moshi): FourSquareApi {
        val retrofit = Retrofit.Builder()
            .baseUrl(url)
            .client(okHttpClient)
            .addConverterFactory(MoshiConverterFactory.create(moshi))
            .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
            .build()
        return retrofit.create(FourSquareApi::class.java)
    }

    @Provides
    fun provideVenuesRepository(
        fourSquareApi: FourSquareApi,
        venuesDao: VenuesDao,
        sharedPrefWrapper: SharedPrefWrapper
    ): Repository {
        return Repository(sharedPrefWrapper, fourSquareApi, venuesDao)
    }

    @Provides
    fun provideSchedulerProvider(): SchedulerProvider {
        return SchedulerProvider()
    }

    @Provides
    fun provideVenuesCacheUseCase(repository: Repository): ExploreVenuesCacheUseCase {
        return ExploreVenuesCacheUseCase(repository)
    }

    @Provides
    fun provideVenuesRealtimeUseCase(
        repository: Repository,
        schedulerProvider: SchedulerProvider
    ): ExploreVenuesRealtimeUseCase {
        return ExploreVenuesRealtimeUseCase(repository, schedulerProvider)
    }

    @Provides
    fun provideVenuesSingleUseCase(
        repository: Repository,
        schedulerProvider: SchedulerProvider
    ): ExploreVenuesSingleUseCase {
        return ExploreVenuesSingleUseCase(repository, schedulerProvider)
    }

    @Provides
    fun provideHomeViewModelFactory(
        schedulerProvider: SchedulerProvider,
        realtimeUseCase: ExploreVenuesRealtimeUseCase,
        singleUseCase: ExploreVenuesSingleUseCase,
        cacheUseCase: ExploreVenuesCacheUseCase
    ): HomeViewModelFactory {
        return HomeViewModelFactory(schedulerProvider, cacheUseCase, realtimeUseCase, singleUseCase)
    }
}
