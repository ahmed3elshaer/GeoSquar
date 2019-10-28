/*
 * *
 *  * Created by Ahmed Elshaer on 10/26/19 4:17 AM
 *  * Copyright (c) 2019 . All rights reserved.
 *  * Last modified 10/25/19 11:58 PM
 *  
 */

package com.ahmed3elshaer.geosquar.common.extensions

import android.location.Location
import com.mapbox.geojson.Point
import com.mapbox.turf.TurfMeasurement

fun String.toCoodinates(): Point {
    val result = split(",")
    return Point.fromLngLat(result[1].toDouble(), result[0].toDouble())
}

fun Point.distanceTo(point: Point): Double = TurfMeasurement.distance(this, point)

fun Any.newLocation(lat: Double, lng: Double): Location {
    val targetLocation = Location("")//provider name is unnecessary
    targetLocation.latitude = lat
    targetLocation.longitude = lng
    return targetLocation
}