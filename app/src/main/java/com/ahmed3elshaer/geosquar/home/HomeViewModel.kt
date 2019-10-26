/*
 * *
 *  * Created by Ahmed Elshaer on 10/26/19 4:17 AM
 *  * Copyright (c) 2019 . All rights reserved.
 *  * Last modified 10/26/19 3:37 AM
 *
 */

package com.ahmed3elshaer.geosquar.home

import android.location.Location
import androidx.lifecycle.MutableLiveData
import com.ahmed3elshaer.geosquar.common.BaseViewModel
import com.ahmed3elshaer.geosquar.common.Event
import com.ahmed3elshaer.geosquar.common.models.VenuesRequest
import com.ahmed3elshaer.geosquar.home.usecases.ExploreVenuesCacheUseCase
import com.ahmed3elshaer.geosquar.home.usecases.ExploreVenuesRealtimeUseCase
import com.ahmed3elshaer.geosquar.home.usecases.ExploreVenuesSingleUseCase

class HomeViewModel(
        private val exploreVenuesRealtimeUseCase: ExploreVenuesRealtimeUseCase,
        private val exploreVenuesSingleUseCase: ExploreVenuesSingleUseCase,
        private val exploreVenuesCacheUseCase: ExploreVenuesCacheUseCase
) : BaseViewModel<HomeViewState>() {
    override val _viewState = MutableLiveData<Event<HomeViewState>>()

    init {
        post(HomeViewState())
    }


    fun checkForCachedVenues() {
        add {
            exploreVenuesCacheUseCase()
                    .compose(applySchedulers())
                    .doOnSubscribe { post(previousValue()?.copy(isLoading = true)) }
                    .subscribe({
                        post(previousValue()?.copy(isLoading = false, venues = it))
                    }, {
                        post(previousValue()?.copy(isLoading = false, error = it))
                    })
        }
    }

    fun exploreVenues(location: Location, realtime: Boolean) {
        if (realtime)
            exploreRealtime(location)
        else
            exploreSingle(location)


    }

    private fun exploreSingle(location: Location) {
        add {
            exploreVenuesSingleUseCase(VenuesRequest("${location.latitude},${location.longitude}"))
                    .compose(applySchedulers())
                    .doOnSubscribe { post(previousValue()?.copy(isLoading = true)) }
                    .subscribe({
                        post(previousValue()?.copy(isLoading = false, venues = it))
                    }, {
                        post(previousValue()?.copy(isLoading = false, error = it))
                    })
        }
    }

    private fun exploreRealtime(location: Location) {
        add {
            exploreVenuesRealtimeUseCase(VenuesRequest("${location.latitude},${location.longitude}"))
                    .compose(applySchedulers())
                    .doOnSubscribe { post(previousValue()?.copy(isLoading = true)) }
                    .subscribe({
                        post(previousValue()?.copy(isLoading = false, venues = it))
                    }, {
                        post(previousValue()?.copy(isLoading = false, error = it))
                    })
        }
    }

}