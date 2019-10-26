/*
 * *
 *  * Created by Ahmed Elshaer on 10/26/19 4:17 AM
 *  * Copyright (c) 2019 . All rights reserved.
 *  * Last modified 10/26/19 3:37 AM
 *
 */

package com.ahmed3elshaer.geosquar.home.usecases

import com.ahmed3elshaer.geosquar.common.Repository
import com.ahmed3elshaer.geosquar.common.baseusecase.BaseVenueUseCase
import com.ahmed3elshaer.geosquar.common.extensions.distanceTo
import com.ahmed3elshaer.geosquar.common.extensions.toCoodinates
import com.ahmed3elshaer.geosquar.common.models.Venue
import com.ahmed3elshaer.geosquar.common.models.VenuesRequest
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