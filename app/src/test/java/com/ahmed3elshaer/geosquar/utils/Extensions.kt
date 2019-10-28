/*
 * *
 *  * Created by Ahmed Elshaer on 10/28/19 7:24 AM
 *  * Copyright (c) 2019 . All rights reserved.
 *  * Last modified 10/28/19 7:24 AM
 *
 */

package com.ahmed3elshaer.geosquar.utils

import org.mockito.Mockito

private fun <T> any(): T {
    Mockito.any<T>()
    return uninitialized()
}

private fun <T> uninitialized(): T = null as T
