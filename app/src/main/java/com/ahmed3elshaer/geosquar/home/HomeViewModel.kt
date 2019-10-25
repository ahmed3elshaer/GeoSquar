package com.ahmed3elshaer.geosquar.home

import androidx.lifecycle.MutableLiveData
import com.ahmed3elshaer.geosquar.common.BaseViewModel
import com.ahmed3elshaer.geosquar.common.Event

class HomeViewModel() : BaseViewModel<Event<HomeViewState>>() {
    override val _viewState = MutableLiveData<Event<HomeViewState>>()
}