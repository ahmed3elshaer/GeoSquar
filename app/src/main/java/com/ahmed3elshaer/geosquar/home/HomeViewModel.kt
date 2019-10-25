package com.ahmed3elshaer.geosquar.home

import androidx.lifecycle.MutableLiveData
import com.ahmed3elshaer.geosquar.common.BaseViewModel

class HomeViewModel() : BaseViewModel<HomeViewState>() {
    override val _viewState = MutableLiveData<HomeViewState>()
}