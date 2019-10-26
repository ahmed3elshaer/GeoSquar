/*
 * *
 *  * Created by Ahmed Elshaer on 10/26/19 4:17 AM
 *  * Copyright (c) 2019 . All rights reserved.
 *  * Last modified 10/24/19 8:53 PM
 *
 */

package com.ahmed3elshaer.geosquar.common

import androidx.annotation.CallSuper
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import io.reactivex.ObservableTransformer
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.disposables.Disposable
import io.reactivex.schedulers.Schedulers


abstract class BaseViewModel<T> : ViewModel() {

    val compositeDisposable = CompositeDisposable()

    protected abstract val _viewState: MutableLiveData<Event<T>>

    val viewState: LiveData<Event<T>> get() = _viewState

    @CallSuper
    override fun onCleared() {
        super.onCleared()
        compositeDisposable.dispose()
    }

    fun add(disposable: () -> Disposable) {
        compositeDisposable.add(disposable())
    }

    fun <X> applySchedulers(): ObservableTransformer<X, X> {
        return ObservableTransformer { up ->
            up.subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
        }
    }

    fun post(state: T?) {
        state?.let {
            _viewState.value = Event(state)
        }

    }

    fun previousValue() = _viewState.value?.peekContent()


}