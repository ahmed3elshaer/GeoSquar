package com.ahmed3elshaer.geosquar.home

import com.ahmed3elshaer.geosquar.common.Repository
import com.ahmed3elshaer.geosquar.common.baseusecase.BaseVenueUseCase
import com.ahmed3elshaer.geosquar.common.models.Venue
import com.ahmed3elshaer.geosquar.common.models.VenuesRequest
import com.ahmed3elshaer.geosquar.common.models.VenuesResponse
import io.reactivex.Observable

class ExploreVenuesSingleUseCase(private val repository: Repository) :
    BaseVenueUseCase(repository) {
    operator fun invoke(
        venuesRequest: VenuesRequest
    ): Observable<List<Venue>> {
        repository.cacheLocation(venuesRequest.coordinates)
        return getVenues(venuesRequest, true)
    }


}