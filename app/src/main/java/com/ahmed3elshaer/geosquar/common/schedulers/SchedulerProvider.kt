/*
 * *
 *  * Created by Ahmed Elshaer on 10/28/19 5:29 AM
 *  * Copyright (c) 2019 . All rights reserved.
 *  * Last modified 10/28/19 5:29 AM
 *
 */

package com.ahmed3elshaer.geosquar.common.schedulers

import io.reactivex.Scheduler
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers

class SchedulerProvider : BaseSchedulerProvider {
    override fun io(): Scheduler = Schedulers.io()

    override fun ui(): Scheduler = AndroidSchedulers.mainThread()

}