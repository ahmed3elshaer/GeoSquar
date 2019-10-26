/*
 * *
 *  * Created by Ahmed Elshaer on 10/26/19 4:17 AM
 *  * Copyright (c) 2019 . All rights reserved.
 *  * Last modified 10/25/19 8:03 PM
 *  
 */

package com.ahmed3elshaer.geosquar.common.models.base

import com.squareup.moshi.Json

interface BaseData

abstract class BaseResponse {
    @Json(name = "meta")
    val meta: Meta = Meta()
    @Json(name = "response")
    abstract val data: BaseData
}

data class Meta(
    @Json(name = "code")
    val code: Int = 0,
    @Json(name = "requestId")
    val requestId: String = ""
)