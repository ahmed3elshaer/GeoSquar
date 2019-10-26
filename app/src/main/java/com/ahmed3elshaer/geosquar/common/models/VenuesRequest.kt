/*
 * *
 *  * Created by Ahmed Elshaer on 10/26/19 4:17 AM
 *  * Copyright (c) 2019 . All rights reserved.
 *  * Last modified 10/25/19 12:27 AM
 *
 */

package com.ahmed3elshaer.geosquar.common.models

import com.squareup.moshi.Json
import retrofit2.http.Query

data class VenuesRequest(@Query("ll") val coordinates: String, @Query("radius") val radius: Long = 1000)