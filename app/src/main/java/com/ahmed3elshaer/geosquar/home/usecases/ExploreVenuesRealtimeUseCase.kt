/*
 * *
 *  * Created by Ahmed Elshaer on 10/26/19 4:17 AM
 *  * Copyright (c) 2019 . All rights reserved.
 *  * Last modified 10/26/19 3:37 AM
 *
 */

package com.ahmed3elshaer.geosquar.home.usecases

import android.annotation.SuppressLint
import com.ahmed3elshaer.geosquar.common.Repository
import com.ahmed3elshaer.geosquar.common.baseusecase.BaseVenueUseCase
import com.ahmed3elshaer.geosquar.common.extensions.distanceTo
import com.ahmed3elshaer.geosquar.common.extensions.toCoodinates
import com.ahmed3elshaer.geosquar.common.models.Venue
import com.ahmed3elshaer.geosquar.common.models.VenuesRequest
import com.jakewharton.rxrelay2.BehaviorRelay
import io.reactivex.Observable
import io.reactivex.schedulers.Schedulers
import io.reactivex.subjects.BehaviorSubject

class ExploreVenuesRealtimeUseCase(private val repository: Repository) :
        BaseVenueUseCase(repository) {
    private var firstLocation: String? = null
    private val realtimeVenuesBehaviour: BehaviorRelay<List<Venue>> = BehaviorRelay.create()
    @SuppressLint("CheckResult")
    operator fun invoke(
            venuesRequest: VenuesRequest
    ): Observable<List<Venue>> {
        if (firstLocation == null) {
            firstLocation = venuesRequest.coordinates
            getVenues(venuesRequest, true)
                    .subscribeOn(Schedulers.io())
                    .subscribe(realtimeVenuesBehaviour)
        } else
            repository.getCachedLocation().apply {
                if (isNotEmpty()) {
                    if (toCoodinates().distanceTo(venuesRequest.coordinates.toCoodinates()) >= 0.5)
                        getVenues(venuesRequest, true)
                                .subscribe(realtimeVenuesBehaviour)

                } else
                    getVenues(venuesRequest, true)
                            .subscribeOn(Schedulers.io())
                            .subscribe(realtimeVenuesBehaviour)

            }
        return realtimeVenuesBehaviour

    }


}