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