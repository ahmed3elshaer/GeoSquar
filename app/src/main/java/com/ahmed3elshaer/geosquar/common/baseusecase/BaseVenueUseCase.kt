package com.ahmed3elshaer.geosquar.common.baseusecase

import com.ahmed3elshaer.geosquar.common.Repository
import com.ahmed3elshaer.geosquar.common.models.VenuesRequest

abstract class BaseVenueUseCase(private val repository: Repository) {
     fun getVenues(venuesRequest: VenuesRequest, shouldCache: Boolean) =
        repository.exploreVenues(venuesRequest)
            .map { it.data.groups.first().venueItems.map { it.venue } }
            .doOnNext {
                if (shouldCache)
                    repository.cacheVenues(it)
            }
}