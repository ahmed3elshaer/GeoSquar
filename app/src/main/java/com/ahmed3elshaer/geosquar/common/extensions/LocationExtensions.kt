/*
 * *
 *  * Created by Ahmed Elshaer on 10/26/19 4:17 AM
 *  * Copyright (c) 2019 . All rights reserved.
 *  * Last modified 10/25/19 11:58 PM
 *  
 */

package com.ahmed3elshaer.geosquar.common.extensions

import com.mapbox.geojson.Point
import com.mapbox.turf.TurfMeasurement

fun String.toCoodinates(): Point {
    val result = split(",")
    return Point.fromLngLat(result[1].toDouble(), result[0].toDouble())
}

fun Point.distanceTo(point: Point): Double = TurfMeasurement.distance(this, point)