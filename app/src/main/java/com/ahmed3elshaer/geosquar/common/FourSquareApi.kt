package com.ahmed3elshaer.geosquar.common

import com.ahmed3elshaer.geosquar.common.models.PhotosResponse
import com.ahmed3elshaer.geosquar.common.models.VenuesRequest
import com.ahmed3elshaer.geosquar.common.models.VenuesResponse
import io.reactivex.Observable
import okhttp3.ResponseBody
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query
import retrofit2.http.Url

interface FourSquareApi {
    @GET("/venues/explore")
    fun exploreVenues(venuesRequest: VenuesRequest): Observable<VenuesResponse>

    @GET("venues/{id}/photos")
    fun getVenuePhotos(@Path("id") venueId: String): Observable<PhotosResponse>

    @GET
    fun getPhotoStream(@Url url: String): Observable<ResponseBody>
}