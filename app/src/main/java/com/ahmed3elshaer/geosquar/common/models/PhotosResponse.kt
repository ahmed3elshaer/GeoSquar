/*
 * *
 *  * Created by Ahmed Elshaer on 10/26/19 4:17 AM
 *  * Copyright (c) 2019 . All rights reserved.
 *  * Last modified 10/25/19 9:23 PM
 *
 */

package com.ahmed3elshaer.geosquar.common.models


import com.ahmed3elshaer.geosquar.common.models.base.BaseData
import com.ahmed3elshaer.geosquar.common.models.base.BaseResponse
import com.squareup.moshi.Json


data class PhotosResponse(
    override val data: PhotosData
) : BaseResponse()
{
    data class Photos(
        @Json(name = "count")
        val count: Int = 0,
        @Json(name = "dupesRemoved")
        val dupesRemoved: Int = 0,
        @Json(name = "items")
        val items: List<PhotoItem>
    )


    data class User(
        @Json(name = "firstName")
        val firstName: String = "",
        @Json(name = "lastName")
        val lastName: String = "",
        @Json(name = "gender")
        val gender: String = "",
        @Json(name = "photo")
        val photo: Photo,
        @Json(name = "id")
        val id: String = ""
    )


    data class Checkin(
        @Json(name = "createdAt")
        val createdAt: Int = 0,
        @Json(name = "timeZoneOffset")
        val timeZoneOffset: Int = 0,
        @Json(name = "id")
        val id: String = "",
        @Json(name = "type")
        val type: String = ""
    )


    data class Photo(
        @Json(name = "prefix")
        val prefix: String = "",
        @Json(name = "suffix")
        val suffix: String = ""
    )


    data class PhotoItem(
        @Json(name = "createdAt")
        val createdAt: Int = 0,
        @Json(name = "checkin")
        val checkin: Checkin,
        @Json(name = "visibility")
        val visibility: String = "",
        @Json(name = "prefix")
        val prefix: String = "",
        @Json(name = "width")
        val width: Int = 0,
        @Json(name = "id")
        val id: String = "",
        @Json(name = "source")
        val source: Source,
        @Json(name = "suffix")
        val suffix: String = "",
        @Json(name = "user")
        val user: User,
        @Json(name = "height")
        val height: Int = 0
    )


    data class Source(
        @Json(name = "name")
        val name: String = "",
        @Json(name = "url")
        val url: String = ""
    )


    data class PhotosData(
        @Json(name = "photos")
        val photos: Photos
    ) : BaseData

}




