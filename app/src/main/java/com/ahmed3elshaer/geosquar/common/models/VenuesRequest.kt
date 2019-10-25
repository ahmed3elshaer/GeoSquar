package com.ahmed3elshaer.geosquar.common.models

import com.squareup.moshi.Json
import retrofit2.http.Query

data class VenuesRequest(@Query("ll") val coordinates: String, @Query("radius") val radius: Long)