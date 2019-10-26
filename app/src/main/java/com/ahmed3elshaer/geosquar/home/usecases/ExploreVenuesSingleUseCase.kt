/*
 * *
 *  * Created by Ahmed Elshaer on 10/26/19 4:17 AM
 *  * Copyright (c) 2019 . All rights reserved.
 *  * Last modified 10/26/19 3:37 AM
 *
 */

package com.ahmed3elshaer.geosquar.home.usecases

import android.location.Location
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