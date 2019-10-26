/*
 * *
 *  * Created by Ahmed Elshaer on 10/26/19 4:17 AM
 *  * Copyright (c) 2019 . All rights reserved.
 *  * Last modified 10/26/19 3:34 AM
 *
 */

package com.ahmed3elshaer.geosquar.common.loader

import com.ahmed3elshaer.geosquar.common.Repository
import com.ahmed3elshaer.geosquar.common.models.Venue
import com.bumptech.glide.Priority
import com.bumptech.glide.load.DataSource
import com.bumptech.glide.load.Key
import com.bumptech.glide.load.Options
import com.bumptech.glide.load.data.DataFetcher
import com.bumptech.glide.load.model.ModelLoader
import com.bumptech.glide.load.model.ModelLoaderFactory
import com.bumptech.glide.load.model.MultiModelLoaderFactory
import io.reactivex.disposables.CompositeDisposable
import java.io.InputStream
import java.security.MessageDigest


class FourSquareImageLoader(private val repository: Repository) :
        ModelLoader<Venue, InputStream> {
    class Factory(private val repository: Repository) :
            ModelLoaderFactory<Venue, InputStream> {

        override fun build(factory: MultiModelLoaderFactory): ModelLoader<Venue, InputStream> {
            return FourSquareImageLoader(repository)
        }

        override fun teardown() {
            // No-op
        }
    }

    override fun buildLoadData(
            model: Venue,
            width: Int,
            height: Int,
            options: Options
    ): ModelLoader.LoadData<InputStream> {
        return ModelLoader.LoadData(
                VenueKey(venue = model),
                FourSquareImageFetcher(venue = model)
        )
    }


    override fun handles(venue: Venue): Boolean {
        return true
    }

    class VenueKey(val venue: Venue) : Key {
        override fun updateDiskCacheKey(messageDigest: MessageDigest) {
            messageDigest.update(venue.id.toByte())
        }

        override fun equals(other: Any?): Boolean {
            if (this === other) return true
            if (other == null || javaClass != other::javaClass) return false

            val key = other as Venue

            return venue.id == key.id
        }

        override fun hashCode(): Int {
            return venue.hashCode()
        }
    }

    inner class FourSquareImageFetcher(private val venue: Venue) :
            DataFetcher<InputStream> {
        private val disposable = CompositeDisposable()
        override fun loadData(
                priority: Priority,
                callback: DataFetcher.DataCallback<in InputStream>
        ) {
            disposable.add(
                    repository.getVenueImages(venue.id)
                            .flatMap {
                                it.data.photos.items.first().let {
                                    (it.prefix + "${it.width}x${it.height}" + it.suffix).let { url ->
                                        repository.getPhotoStream(url)
                                    }

                                }
                            }
                            .map { it.byteStream() }
                            .subscribe(
                                    {
                                        callback.onDataReady(it)
                                    }, {
                                callback.onLoadFailed(Exception(it))

                            })
            )

        }

        override fun getDataClass(): Class<InputStream> = InputStream::class.java

        override fun cleanup() {
            disposable.dispose()
        }


        override fun getDataSource(): DataSource = DataSource.REMOTE

        override fun cancel() {
            disposable.dispose()
        }

    }
}
