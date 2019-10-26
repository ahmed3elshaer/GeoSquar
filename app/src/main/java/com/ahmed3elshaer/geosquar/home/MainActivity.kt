/*
 * *
 *  * Created by Ahmed Elshaer on 10/26/19 4:17 AM
 *  * Copyright (c) 2019 . All rights reserved.
 *  * Last modified 10/26/19 3:37 AM
 *
 */

package com.ahmed3elshaer.geosquar.home

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.lifecycle.Observer
import com.ahmed3elshaer.geosquar.R
import com.ahmed3elshaer.geosquar.common.Event
import com.ahmed3elshaer.geosquar.common.extensions.isNetworkAvailable
import com.ahmed3elshaer.geosquar.common.extensions.isRealtime
import com.ahmed3elshaer.geosquar.common.location.RxLocationExt
import com.ahmed3elshaer.geosquar.common.models.Venue
import io.reactivex.disposables.CompositeDisposable
import org.koin.android.ext.android.inject

class MainActivity : AppCompatActivity() {
    private val rxLocation: RxLocationExt = RxLocationExt()
    private val compositeDisposable = CompositeDisposable()
    private val viewModel: HomeViewModel by inject()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        viewModel.viewState.observe(this, Observer {
            render(it)
        })
        getLocation()
    }

    private fun initModeChange() {
        switch_mode.isChecked = isRealtime()
        switch_mode.setOnCheckedChangeListener { buttonView, isChecked ->
            changeMode(isChecked)
            getLocation()
        }
    }

    private fun initVenuesList() {
        adapter = VenuesAdapter()
        recycler_venues.itemAnimator = DefaultItemAnimator()
        recycler_venues.addItemDecoration(DividerItemDecoration(this, RecyclerView.VERTICAL))
        recycler_venues.adapter = adapter
    }
    private fun render(event: Event<HomeViewState>) {
        event.getContentIfNotHandled()?.apply {
            renderLoading(isLoading)
            error?.let {
                error.printStackTrace()
                renderError(error)
                return
            }
            venues?.let {
                if (it.isEmpty())
                    renderEmptyState()
                else
                    renderVenues(it)

            }

        }
    }

    private fun renderVenues(venues: List<Venue>) {
        //TODO
    }

    private fun renderEmptyState() {
        //TODO
    }

    private fun getLocation() {
        if (isNetworkAvailable()) {
            rxLocation.stopLocationUpdates()
            compositeDisposable.add(
                    rxLocation.locations(this, isRealtime())
                            .doOnSubscribe { renderLoading(true) }
                            .subscribe({ location ->
                                viewModel.exploreVenues(location, isRealtime())

                            },
                                    { error: Throwable ->
                                        renderError(error)
                                    })
            )
        } else
            viewModel.checkForCachedVenues()

    }

    private fun renderError(error: Throwable) {
        //TODO
    }

    private fun renderLoading(shouldLoad: Boolean) {
        //TODO
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        rxLocation.onActivityResult(requestCode, resultCode, data)

    }

    override fun onRequestPermissionsResult(
            requestCode: Int,
            permissions: Array<out String>,
            grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        rxLocation.onRequestPermissionsResult(requestCode, permissions, grantResults)
    }
}
