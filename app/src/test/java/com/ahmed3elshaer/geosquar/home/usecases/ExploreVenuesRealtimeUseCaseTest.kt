/*
 * *
 *  * Created by Ahmed Elshaer on 10/28/19 6:21 AM
 *  * Copyright (c) 2019 . All rights reserved.
 *  * Last modified 10/28/19 6:21 AM
 *
 */

package com.ahmed3elshaer.geosquar.home.usecases

import android.location.Location
import com.ahmed3elshaer.geosquar.common.Repository
import com.ahmed3elshaer.geosquar.common.models.Venue
import com.ahmed3elshaer.geosquar.common.models.VenuesRequest
import com.ahmed3elshaer.geosquar.common.models.VenuesResponse
import com.ahmed3elshaer.geosquar.common.schedulers.SchedulerProviderTest
import io.reactivex.Observable
import org.junit.Before
import org.junit.Test
import org.mockito.Mock
import org.mockito.Mockito.`when`
import org.mockito.MockitoAnnotations

class ExploreVenuesRealtimeUseCaseTest {
    @Mock
    private lateinit var repository: Repository
    private lateinit var venuesList: List<VenuesResponse.VenueItem>
    private lateinit var exploreVenuesRealtimeUseCase: ExploreVenuesRealtimeUseCase
    private lateinit var venuesRequest: VenuesRequest
    private lateinit var venuesResponse: VenuesResponse
    private val lat = 30.0456862
    private val lng = 31.2195405
    @Before
    fun setupRealtimeUseCase() {
        MockitoAnnotations.initMocks(this)
        exploreVenuesRealtimeUseCase =
            ExploreVenuesRealtimeUseCase(repository, SchedulerProviderTest())
        venuesRequest = VenuesRequest("30.0456862,31.2195405")
        val location = VenuesResponse.Location(
            formattedAddress = listOf(),
            labeledLatLngs = listOf()
        )

        venuesList = mutableListOf(
            VenuesResponse.VenueItem(
                venue = Venue(location = location),
                reasons = VenuesResponse.Reasons()
            ),
            VenuesResponse.VenueItem(
                venue = Venue(location = location),
                reasons = VenuesResponse.Reasons()
            ),
            VenuesResponse.VenueItem(
                venue = Venue(location = location),
                reasons = VenuesResponse.Reasons()
            )

        )
        venuesResponse = VenuesResponse(
            data = VenuesResponse.VenueData(
                groups = listOf(VenuesResponse.Group(venueItems = venuesList)),
                suggestedBounds = VenuesResponse.SuggestedBounds()
            )
        )
    }

    @Test
    fun firstRequest() {
        `when`(repository.exploreVenues(venuesRequest)).thenReturn(
            Observable.just(
                venuesResponse
            )
        )
        exploreVenuesRealtimeUseCase(venuesRequest)
            .test().apply {
                assertNoErrors()
                assertValue {
                    it.size == 3
                }
            }.dispose()
    }

    @Test
    fun smallDistanceRequest() {
        `when`(repository.exploreVenues(venuesRequest)).thenReturn(
            Observable.just(
                venuesResponse
            )
        )
        // first emit to make sure it's not the first call (make location variable !=null)
        exploreVenuesRealtimeUseCase(venuesRequest)
            .test().apply {
                assertNoErrors()
                assertValue {
                    it.size == 3
                }
            }.dispose()
        `when`(repository.getCachedLocation()).thenReturn("$lat,$lng")
        // first trial with the same location should not return data (count 1)
        exploreVenuesRealtimeUseCase(venuesRequest)
            .test().apply {
                assertNoErrors()
                assertValueCount(1)
            }.dispose()
        // second trial with the same location should not return data (count 1)
        exploreVenuesRealtimeUseCase(venuesRequest)
            .test().apply {
                assertNoErrors()
                assertValueCount(1)
            }.dispose()
    }

    @Test
    fun whenNoCachedLocation() {
        `when`(repository.exploreVenues(venuesRequest)).thenReturn(
            Observable.just(
                venuesResponse
            )
        )
        // first emit to make sure it's not the first call (make location variable !=null)
        // and the first venue name is empty
        exploreVenuesRealtimeUseCase(venuesRequest)
            .test().apply {
                assertNoErrors()
                assertValue {
                    it.size == 3
                    it.first().name == ""
                }
            }.dispose()
        `when`(repository.exploreVenues(venuesRequest)).thenReturn(
            Observable.just(
                venuesResponse
            )
        )
        `when`(repository.getCachedLocation()).thenReturn("")
        `when`(repository.exploreVenues(venuesRequest)).thenReturn(
            Observable.just(
                venuesResponse.apply {
                    data.groups.first().venueItems.first().venue.name = "second"
                }
            )
        )
        // trying with the same location should  return data with first venue name == second
        exploreVenuesRealtimeUseCase(venuesRequest)
            .test().apply {
                assertNoErrors()
                assertValue { it.first().name == "second" }
            }.dispose()
    }
}
