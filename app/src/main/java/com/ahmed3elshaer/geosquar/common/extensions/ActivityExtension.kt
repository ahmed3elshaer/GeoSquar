/*
 * *
 *  * Created by Ahmed Elshaer on 10/26/19 4:17 AM
 *  * Copyright (c) 2019 . All rights reserved.
 *  * Last modified 10/26/19 3:44 AM
 *
 */

package com.ahmed3elshaer.geosquar.common.extensions

import android.app.Activity
import android.content.Context
import android.net.ConnectivityManager
import android.net.NetworkCapabilities
import android.net.wifi.WifiManager
import android.os.Build
import android.provider.Settings
import com.ahmed3elshaer.geosquar.common.SharedPrefWrapper
import com.ahmed3elshaer.geosquar.common.SharedPrefWrapper.Companion.MODE
import com.ahmed3elshaer.geosquar.common.SharedPrefWrapper.Companion.REAL_TIME
import com.ahmed3elshaer.geosquar.common.SharedPrefWrapper.Companion.SINGLE
import org.koin.android.ext.android.inject


fun Activity.isNetworkAvailable(): Boolean {
    val connectivityManager =
            this.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
        val nw = connectivityManager.activeNetwork ?: return false
        val actNw = connectivityManager.getNetworkCapabilities(nw) ?: return false
        return when {
            actNw.hasTransport(NetworkCapabilities.TRANSPORT_WIFI) -> true
            actNw.hasTransport(NetworkCapabilities.TRANSPORT_CELLULAR) -> true
            //for other device how are able to connect with Ethernet
            actNw.hasTransport(NetworkCapabilities.TRANSPORT_ETHERNET) -> true
            else -> false
        }
    } else {
        val nwInfo = connectivityManager.activeNetworkInfo ?: return false
        return nwInfo.isConnected
    }
}

fun Activity.isInFlightMode(): Boolean {
    return Settings.Global.getInt(this.contentResolver, Settings.Global.AIRPLANE_MODE_ON, 0) != 0

}

fun Activity.isRealtime(): Boolean {
    val sharedPrefWrapper: SharedPrefWrapper by inject()
    return sharedPrefWrapper.getString(MODE, defValue = REAL_TIME) == REAL_TIME
}

fun Activity.changeMode(isRealtime: Boolean) {
    val sharedPrefWrapper: SharedPrefWrapper by inject()
    if (isRealtime)
        sharedPrefWrapper.saveString(MODE, REAL_TIME)
    else
        sharedPrefWrapper.saveString(MODE, SINGLE)
}