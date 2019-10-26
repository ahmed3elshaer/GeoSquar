package com.ahmed3elshaer.geosquar.common.extensions

import com.mapbox.geojson.Point
import com.mapbox.turf.TurfMeasurement

fun String.toCoodinates(): Point {
    val result = split(",")
    return Point.fromLngLat(result[1].toDouble(), result[0].toDouble())
}

fun Point.distanceTo(point: Point): Double = TurfMeasurement.distance(this, point)