package com.ahmed3elshaer.geosquar.common.local

import androidx.room.TypeConverter
import com.ahmed3elshaer.geosquar.common.models.VenuesResponse
import com.google.gson.Gson

class VenuesConverter {
    @TypeConverter
    fun pageToJson(value: VenuesResponse.VenuePage): String {
        return Gson().toJson(value)
    }

    @TypeConverter
    fun stringToPage(value: String): VenuesResponse.VenuePage {
        return Gson().fromJson(value, VenuesResponse.VenuePage::class.java)
    }

    @TypeConverter
    fun locationToJson(value: VenuesResponse.Location): String {
        return Gson().toJson(value)
    }

    @TypeConverter
    fun stringToLocation(value: String): VenuesResponse.Location {
        return Gson().fromJson(value, VenuesResponse.Location::class.java)
    }

    @TypeConverter
    fun listToJson(value: List<VenuesResponse.Category>): String {
        return Gson().toJson(value)
    }

    @TypeConverter
    fun stringToList(value: String): List<VenuesResponse.Category> {
        return Gson().fromJson(value, Array<VenuesResponse.Category>::class.java).toList()
    }
}