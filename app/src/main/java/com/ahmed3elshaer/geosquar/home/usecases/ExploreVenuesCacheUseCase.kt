package com.ahmed3elshaer.geosquar.home

import com.ahmed3elshaer.geosquar.common.Repository
import com.ahmed3elshaer.geosquar.common.baseusecase.BaseVenueUseCase
import com.ahmed3elshaer.geosquar.common.models.Venue
import com.ahmed3elshaer.geosquar.common.models.VenuesRequest
import com.ahmed3elshaer.geosquar.common.models.VenuesResponse
import io.reactivex.Observable

class ExploreVenuesCacheUseCase(private val repository: Repository) {
    operator fun invoke(
    ): Observable<List<Venue>> {
        return repository.getVenuesCache()
    }


}