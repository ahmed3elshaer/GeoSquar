package com.ahmed3elshaer.geosquar.common

import com.ahmed3elshaer.geosquar.common.models.VenuesRequest

class Repository(
    private val sharedPrefWrapper: SharedPrefWrapper,
    private val fourSquareApi: FourSquareApi
) {

    fun exploreVenues(venuesRequest: VenuesRequest) = fourSquareApi.exploreVenues(venuesRequest)

    fun getVenueImages(venueId: String) = fourSquareApi.getVenuePhotos(venueId)

    fun getPhotoStream(url: String) = fourSquareApi.getPhotoStream(url)

}