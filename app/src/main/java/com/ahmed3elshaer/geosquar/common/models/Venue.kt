package com.ahmed3elshaer.geosquar.common.models

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.squareup.moshi.Json

@Entity(tableName = "venues")
data class Venue(
    @PrimaryKey(autoGenerate = true) val id_: Long,
    @ColumnInfo(name = "popularityByGeo")
    @Json(name = "popularityByGeo")
    val popularityByGeo: Double = 0.0,
    @ColumnInfo(name = "venuePage")
    @Json(name = "venuePage")
    val venuePage: VenuesResponse.VenuePage,
    @ColumnInfo(name = "name")
    @Json(name = "name")
    val name: String = "",
    @ColumnInfo(name = "location")
    @Json(name = "location")
    val location: VenuesResponse.Location,
    @ColumnInfo(name = "id")
    @Json(name = "id")
    val id: String = "",
    @ColumnInfo(name = "categories")
    @Json(name = "categories")
    val categories: List<VenuesResponse.Category>
)