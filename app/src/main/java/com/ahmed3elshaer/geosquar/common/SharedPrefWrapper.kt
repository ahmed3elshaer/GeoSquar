/*
 * *
 *  * Created by Ahmed Elshaer on 10/26/19 4:17 AM
 *  * Copyright (c) 2019 . All rights reserved.
 *  * Last modified 10/25/19 11:46 PM
 *
 */

package com.ahmed3elshaer.geosquar.common

import android.content.SharedPreferences

class SharedPrefWrapper(private val sharedPreferences: SharedPreferences) {

    fun saveString(key: String, value: String) {
        sharedPreferences.edit().putString(key, value).apply()
    }

    fun deleteKey(key: String) {
        sharedPreferences.edit().remove(key).apply()
    }

    fun getString(key: String, defValue: String = ""): String {
        return sharedPreferences.getString(key, defValue)!!
    }

    fun getStringOrNull(key: String): String? {
        return sharedPreferences.getString(key, null)
    }

    fun saveInt(key: String, value: Int) {
        sharedPreferences.edit().putInt(key, value).apply()
    }

    fun getInt(key: String, defValue: Int = -1): Int {
        return sharedPreferences.getInt(key, defValue)
    }

    fun saveBoolean(key: String, value: Boolean) {
        sharedPreferences.edit().putBoolean(key, value).apply()
    }

    fun getBoolean(key: String, defValue: Boolean = false): Boolean {
        return sharedPreferences.getBoolean(key, defValue)
    }

    fun clear() {
        sharedPreferences.edit().clear().apply()
    }

    companion object {
        const val LOCATION_KEY = "locationKey"
        const val MODE = "geoSquareMode"
        const val REAL_TIME = "realtime"
        const val SINGLE = "single"
    }
}
