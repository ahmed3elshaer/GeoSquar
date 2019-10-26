/*
 * *
 *  * Created by Ahmed Elshaer on 10/26/19 4:17 AM
 *  * Copyright (c) 2019 . All rights reserved.
 *  * Last modified 10/24/19 9:31 PM
 *
 */

package com.ahmed3elshaer.geosquar.common

import okhttp3.Interceptor
import okhttp3.Response

class AuthInterceptor() : Interceptor {
    override fun intercept(chain: Interceptor.Chain): Response {
        val originalRequest = chain.request()
        return chain.proceed(originalRequest.newBuilder().apply {
        url(originalRequest.url.toString()+"&client_id=VZC2GFB3EJ35BGUXAJRLU12RGP2BFLREFOYNAQFIOT2WFTJI&client_secret=0LPQQUCUA1YZ0IPUBKNJX1DEQDGBMUXNNGPZWDIHKE0HLSPF")
        }.build())


    }

}