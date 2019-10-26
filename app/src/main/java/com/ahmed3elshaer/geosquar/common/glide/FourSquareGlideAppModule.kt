package com.ahmed3elshaer.geosquar.common.glide

import android.content.Context
import com.ahmed3elshaer.geosquar.common.Repository
import com.ahmed3elshaer.geosquar.common.models.Venue
import com.ahmed3elshaer.geosquar.common.models.VenuesResponse
import com.bumptech.glide.module.AppGlideModule
import com.bumptech.glide.Glide
import com.bumptech.glide.Registry
import com.bumptech.glide.annotation.GlideModule
import org.koin.standalone.KoinComponent
import org.koin.standalone.inject
import java.io.InputStream

@GlideModule
class FourSquareGlideAppModule() : AppGlideModule(),KoinComponent{

    override fun registerComponents(context: Context, glide: Glide, registry: Registry) {
        val repository :Repository by inject()
        registry.prepend(
            Venue::class.java,
            InputStream::class.java,
            FourSquareImageLoader.Factory(repository)
        )
    }
}