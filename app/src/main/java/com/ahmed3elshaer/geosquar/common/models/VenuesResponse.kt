package com.ahmed3elshaer.geosquar.common.models


import com.ahmed3elshaer.geosquar.common.models.base.BaseData
import com.ahmed3elshaer.geosquar.common.models.base.BaseResponse
import com.ahmed3elshaer.geosquar.common.models.base.Meta
import com.squareup.moshi.Json


data class VenuesResponse(
    override val data: VenueData
) : BaseResponse() {

    data class Warning(
        @Json(name = "text")
        val text: String = ""
    )


    data class VenuePage(
        @Json(name = "id")
        val id: String = ""
    )


    data class Venue(
        @Json(name = "popularityByGeo")
        val popularityByGeo: Double = 0.0,
        @Json(name = "venuePage")
        val venuePage: VenuePage,
        @Json(name = "name")
        val name: String = "",
        @Json(name = "location")
        val location: Location,
        @Json(name = "id")
        val id: String = "",
        @Json(name = "categories")
        val categories: List<Category>
    )


    data class Sw(
        @Json(name = "lng")
        val lng: Double = 0.0,
        @Json(name = "lat")
        val lat: Double = 0.0
    )


    data class Category(
        @Json(name = "pluralName")
        val pluralName: String = "",
        @Json(name = "name")
        val name: String = "",
        @Json(name = "icon")
        val icon: Icon,
        @Json(name = "id")
        val id: String = "",
        @Json(name = "shortName")
        val shortName: String = "",
        @Json(name = "primary")
        val primary: Boolean = false
    )


    data class VenueItem(
        @Json(name = "venue")
        val venue: Venue,
        @Json(name = "reasons")
        val reasons: Reasons
    )


    data class Reasons(
        @Json(name = "count")
        val count: Int = 0,
        @Json(name = "items")
        val items: List<VenueItem>
    )


    data class Group(
        @Json(name = "name")
        val name: String = "",
        @Json(name = "type")
        val type: String = "",
        @Json(name = "items")
        val venueItems: List<VenueItem>
    )


    data class VenueData(
        @Json(name = "totalResults")
        val totalResults: Int = 0,
        @Json(name = "suggestedRadius")
        val suggestedRadius: Int = 0,
        @Json(name = "headerFullLocation")
        val headerFullLocation: String = "",
        @Json(name = "warning")
        val warning: Warning,
        @Json(name = "headerLocationGranularity")
        val headerLocationGranularity: String = "",
        @Json(name = "groups")
        val groups: List<Group>,
        @Json(name = "suggestedBounds")
        val suggestedBounds: SuggestedBounds,
        @Json(name = "headerLocation")
        val headerLocation: String = ""
    ) : BaseData


    data class Ne(
        @Json(name = "lng")
        val lng: Double = 0.0,
        @Json(name = "lat")
        val lat: Double = 0.0
    )


    data class LabeledLatLngsItem(
        @Json(name = "lng")
        val lng: Double = 0.0,
        @Json(name = "label")
        val label: String = "",
        @Json(name = "lat")
        val lat: Double = 0.0
    )


    data class Icon(
        @Json(name = "prefix")
        val prefix: String = "",
        @Json(name = "suffix")
        val suffix: String = ""
    )


    data class SuggestedBounds(
        @Json(name = "sw")
        val sw: Sw,
        @Json(name = "ne")
        val ne: Ne
    )


    data class Location(
        @Json(name = "cc")
        val cc: String = "",
        @Json(name = "country")
        val country: String = "",
        @Json(name = "address")
        val address: String = "",
        @Json(name = "labeledLatLngs")
        val labeledLatLngs: List<LabeledLatLngsItem>?,
        @Json(name = "lng")
        val lng: Double = 0.0,
        @Json(name = "distance")
        val distance: Int = 0,
        @Json(name = "formattedAddress")
        val formattedAddress: List<String>?,
        @Json(name = "city")
        val city: String = "",
        @Json(name = "postalCode")
        val postalCode: String = "",
        @Json(name = "state")
        val state: String = "",
        @Json(name = "crossStreet")
        val crossStreet: String = "",
        @Json(name = "lat")
        val lat: Double = 0.0
    )

}


