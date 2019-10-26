package com.ahmed3elshaer.geosquar.home

import com.ahmed3elshaer.geosquar.common.models.Venue
import com.ahmed3elshaer.geosquar.common.models.VenuesResponse

data class HomeViewState(
    val isLoading: Boolean = false,
    val venues: List<Venue>
)