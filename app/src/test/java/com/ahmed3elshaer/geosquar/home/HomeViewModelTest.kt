/*
 * *
 *  * Created by Ahmed Elshaer on 10/28/19 4:32 AM
 *  * Copyright (c) 2019 . All rights reserved.
 *  * Last modified 10/28/19 4:32 AM
 *
 */

package com.ahmed3elshaer.geosquar.home


import android.location.Location
import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.ahmed3elshaer.geosquar.utils.LiveDataTestUtil
import com.ahmed3elshaer.geosquar.common.models.Venue
import com.ahmed3elshaer.geosquar.common.models.VenuesRequest
import com.ahmed3elshaer.geosquar.common.models.VenuesResponse
import com.ahmed3elshaer.geosquar.common.schedulers.SchedulerProviderTest
import com.ahmed3elshaer.geosquar.home.usecases.ExploreVenuesCacheUseCase
import com.ahmed3elshaer.geosquar.home.usecases.ExploreVenuesRealtimeUseCase
import com.ahmed3elshaer.geosquar.home.usecases.ExploreVenuesSingleUseCase
import io.reactivex.Observable
import junit.framework.Assert.assertEquals
import junit.framework.Assert.assertFalse
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.mockito.Mock
import org.mockito.Mockito.`when`
import org.mockito.MockitoAnnotations


class HomeViewModelTest {
    @get:Rule
    var instantExecutorRule = InstantTaskExecutorRule()
    @Mock
    private lateinit var viewModel: HomeViewModel
    private lateinit var venuesList: List<Venue>
    @Mock
    private lateinit var exploreVenuesRealtimeUseCase: ExploreVenuesRealtimeUseCase
    @Mock
    private lateinit var exploreVenuesSingleUseCase: ExploreVenuesSingleUseCase
    @Mock
    private lateinit var exploreVenuesCacheUseCase: ExploreVenuesCacheUseCase
    private lateinit var venuesRequest: VenuesRequest
    @Mock
    private lateinit var location: Location
    private val throwable = Throwable("test error")

    private val lat = 30.0456862

    private val lng = 31.2195405

    @Before
    fun setupHomeViewModel() {
        // Mockito has a very convenient way to inject mocks by using the @Mock annotation. To
        // inject the mocks in the test the initMocks method needs to be called.
        MockitoAnnotations.initMocks(this)
        viewModel = HomeViewModel(
            SchedulerProviderTest(),
            exploreVenuesRealtimeUseCase,
            exploreVenuesSingleUseCase, exploreVenuesCacheUseCase
        )
        venuesRequest = VenuesRequest("30.0456862,31.2195405")
        val location = VenuesResponse.Location(
            formattedAddress = listOf(),
            labeledLatLngs = listOf()
        )
        venuesList = mutableListOf(
            Venue(location = location),
            Venue(location = location),
            Venue(location = location)
        )


    }

    @Test
    fun realtimeVenues() {
        setupLocation()
        `when`(exploreVenuesRealtimeUseCase.invoke(venuesRequest)).thenReturn(
            Observable.just(
                venuesList, venuesList.toMutableList().apply {
                    addAll(venuesList)
                }.toList()
            )
        )
        viewModel.exploreVenues(location, true)
        assertFalse(LiveDataTestUtil.getValue(viewModel.viewState).peekContent().isLoading)
        assertEquals(LiveDataTestUtil.getValue(viewModel.viewState).peekContent().error, null)
        assertEquals(LiveDataTestUtil.getValue(viewModel.viewState).peekContent().venues?.size, 6)

    }

    @Test
    fun realtimeVenuesError() {
        setupLocation()
        `when`(exploreVenuesRealtimeUseCase.invoke(venuesRequest)).thenReturn(
            Observable.error(
                throwable
            )
        )
        viewModel.exploreVenues(location, true)
        assertFalse(LiveDataTestUtil.getValue(viewModel.viewState).peekContent().isLoading)
        assertEquals(LiveDataTestUtil.getValue(viewModel.viewState).peekContent().error, throwable)
        assertEquals(LiveDataTestUtil.getValue(viewModel.viewState).peekContent().venues, null)

    }

    @Test
    fun singleVenues() {
        setupLocation()
        `when`(exploreVenuesSingleUseCase.invoke(venuesRequest)).thenReturn(
            Observable.just(
                venuesList
            )
        )
        viewModel.exploreVenues(location, false)
        assertFalse(LiveDataTestUtil.getValue(viewModel.viewState).peekContent().isLoading)
        assertEquals(LiveDataTestUtil.getValue(viewModel.viewState).peekContent().error, null)
        assertEquals(LiveDataTestUtil.getValue(viewModel.viewState).peekContent().venues?.size, 3)

    }

    @Test
    fun singleVenuesError() {
        setupLocation()
        `when`(exploreVenuesSingleUseCase.invoke(venuesRequest)).thenReturn(
            Observable.error(
                throwable
            )
        )
        viewModel.exploreVenues(location, false)
        assertFalse(LiveDataTestUtil.getValue(viewModel.viewState).peekContent().isLoading)
        assertEquals(LiveDataTestUtil.getValue(viewModel.viewState).peekContent().error, throwable)
        assertEquals(LiveDataTestUtil.getValue(viewModel.viewState).peekContent().venues, null)

    }

    @Test
    fun cachedVenues() {
        `when`(exploreVenuesCacheUseCase.invoke()).thenReturn(
            Observable.just(
                venuesList
            )
        )
        viewModel.checkForCachedVenues()
        assertFalse(LiveDataTestUtil.getValue(viewModel.viewState).peekContent().isLoading)
        assertEquals(LiveDataTestUtil.getValue(viewModel.viewState).peekContent().error, null)
        assertEquals(LiveDataTestUtil.getValue(viewModel.viewState).peekContent().venues?.size, 3)

    }

    @Test
    fun cachedVenuesError() {
        `when`(exploreVenuesCacheUseCase.invoke()).thenReturn(
            Observable.error(
                throwable
            )
        )
        viewModel.checkForCachedVenues()
        assertFalse(LiveDataTestUtil.getValue(viewModel.viewState).peekContent().isLoading)
        assertEquals(LiveDataTestUtil.getValue(viewModel.viewState).peekContent().error, throwable)
        assertEquals(LiveDataTestUtil.getValue(viewModel.viewState).peekContent().venues, null)

    }

    private fun setupLocation() {
        `when`(location.latitude).thenReturn(lat)
        `when`(location.longitude).thenReturn(lng)
    }

}