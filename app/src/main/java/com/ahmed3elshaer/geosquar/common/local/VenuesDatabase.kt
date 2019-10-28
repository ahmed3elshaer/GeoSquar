/*
 * *
 *  * Created by Ahmed Elshaer on 10/26/19 4:17 AM
 *  * Copyright (c) 2019 . All rights reserved.
 *  * Last modified 10/26/19 3:34 AM
 *
 */

package com.ahmed3elshaer.geosquar.common.local

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.ahmed3elshaer.geosquar.common.models.Venue

@TypeConverters(VenuesConverter::class)
@Database(
    entities = [Venue::class],
    version = 3,
    exportSchema = false
)
abstract class VenuesDatabase : RoomDatabase() {
    abstract fun moviesDao(): VenuesDao

    companion object {
        fun getInstance(context: Context): VenuesDatabase =
            Room.databaseBuilder(context, VenuesDatabase::class.java, "VenueDatabase")
                .fallbackToDestructiveMigration()
                .build()
    }
}
