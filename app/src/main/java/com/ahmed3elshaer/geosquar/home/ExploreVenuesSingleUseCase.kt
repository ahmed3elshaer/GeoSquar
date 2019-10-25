package com.ahmed3elshaer.geosquar.home

import com.ahmed3elshaer.geosquar.common.Repository
import com.ahmed3elshaer.geosquar.common.models.VenuesRequest
import com.ahmed3elshaer.geosquar.common.models.VenuesResponse
import io.reactivex.Observable

class ExploreVenuesSingleUseCase(private val repository: Repository) {
    operator fun invoke(
        venuesRequest: VenuesRequest
    ): Observable<List<VenuesResponse.Venue>> {
        repository.cacheLocation(venuesRequest.coordinates)
        return repository.exploreVenues(venuesRequest)
            .map { it.data.groups.first().venueItems.map { it.venue } }
    }


}