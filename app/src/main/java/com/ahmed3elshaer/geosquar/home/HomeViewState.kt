/*
 * *
 *  * Created by Ahmed Elshaer on 10/26/19 4:17 AM
 *  * Copyright (c) 2019 . All rights reserved.
 *  * Last modified 10/26/19 3:54 AM
 *
 */

package com.ahmed3elshaer.geosquar.home

import com.ahmed3elshaer.geosquar.common.models.Venue

data class HomeViewState(
        val isLoading: Boolean = false,
        val venues: List<Venue>? = null,
        val error: Throwable? = null
)