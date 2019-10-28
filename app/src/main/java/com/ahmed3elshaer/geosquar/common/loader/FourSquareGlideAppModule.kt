/*
 * *
 *  * Created by Ahmed Elshaer on 10/26/19 4:17 AM
 *  * Copyright (c) 2019 . All rights reserved.
 *  * Last modified 10/26/19 3:34 AM
 *
 */

package com.ahmed3elshaer.geosquar.common.loader

import android.content.Context
import com.ahmed3elshaer.geosquar.common.Repository
import com.ahmed3elshaer.geosquar.common.models.Venue
import com.bumptech.glide.Glide
import com.bumptech.glide.Registry
import com.bumptech.glide.annotation.GlideModule
import com.bumptech.glide.module.AppGlideModule
import java.io.InputStream
import org.koin.standalone.KoinComponent
import org.koin.standalone.inject

@GlideModule
class FourSquareGlideAppModule : AppGlideModule(), KoinComponent {

    override fun registerComponents(context: Context, glide: Glide, registry: Registry) {
        val repository: Repository by inject()
        registry.prepend(
                Venue::class.java,
                InputStream::class.java,
                FourSquareImageLoader.Factory(repository)
        )
    }
}
