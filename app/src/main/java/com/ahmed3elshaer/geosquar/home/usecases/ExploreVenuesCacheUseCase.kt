/*
 * *
 *  * Created by Ahmed Elshaer on 10/26/19 4:17 AM
 *  * Copyright (c) 2019 . All rights reserved.
 *  * Last modified 10/26/19 3:37 AM
 *
 */

package com.ahmed3elshaer.geosquar.home.usecases

import com.ahmed3elshaer.geosquar.common.Repository
import com.ahmed3elshaer.geosquar.common.models.Venue
import io.reactivex.Observable

class ExploreVenuesCacheUseCase(private val repository: Repository) {
    operator fun invoke(): Observable<List<Venue>> {
        return repository.getVenuesCache()
    }
}
