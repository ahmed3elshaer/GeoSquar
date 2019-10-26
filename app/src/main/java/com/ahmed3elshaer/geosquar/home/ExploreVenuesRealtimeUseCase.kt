package com.ahmed3elshaer.geosquar.home

import com.ahmed3elshaer.geosquar.common.Repository
import com.ahmed3elshaer.geosquar.common.baseusecase.BaseVenueUseCase
import com.ahmed3elshaer.geosquar.common.extensions.distanceTo
import com.ahmed3elshaer.geosquar.common.extensions.toCoodinates
import com.ahmed3elshaer.geosquar.common.models.Venue
import com.ahmed3elshaer.geosquar.common.models.VenuesRequest
import com.ahmed3elshaer.geosquar.common.models.VenuesResponse
import io.reactivex.Observable

class ExploreVenuesRealtimeUseCase(private val repository: Repository) :
    BaseVenueUseCase(repository) {
    operator fun invoke(
        venuesRequest: VenuesRequest
    ): Observable<List<Venue>> {
        repository.getCachedLocation().apply {
            return if (isNotEmpty()) {
                return if (toCoodinates().distanceTo(venuesRequest.coordinates.toCoodinates()) >= 500)
                    getVenues(venuesRequest, true)
                else
                    Observable.empty()
            } else
                getVenues(venuesRequest, true)

        }

    }


}