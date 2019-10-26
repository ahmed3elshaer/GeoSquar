/*
 * *
 *  * Created by Ahmed Elshaer on 10/26/19 4:17 AM
 *  * Copyright (c) 2019 . All rights reserved.
 *  * Last modified 10/26/19 3:34 AM
 *
 */

package com.ahmed3elshaer.geosquar.common.local

import androidx.room.*
import com.ahmed3elshaer.geosquar.common.models.Venue
import io.reactivex.Observable

@Dao
@TypeConverters(VenuesConverter::class)
interface VenuesDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(venue: Venue)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertAll(vararg venues: Venue)

    @Query("SELECT * FROM venues")
    fun allVenues(): Observable<List<Venue>>

    @Query("DELETE  FROM venues")
    fun clearAll()
}