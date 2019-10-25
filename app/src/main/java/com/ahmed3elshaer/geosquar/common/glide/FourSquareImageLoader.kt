package com.ahmed3elshaer.geosquar.common.glide

import com.ahmed3elshaer.geosquar.common.Repository
import com.ahmed3elshaer.geosquar.common.models.PhotosResponse
import com.ahmed3elshaer.geosquar.common.models.VenuesResponse
import com.bumptech.glide.Priority
import com.bumptech.glide.load.DataSource
import com.bumptech.glide.load.Key
import com.bumptech.glide.load.Options
import com.bumptech.glide.load.data.DataFetcher
import com.bumptech.glide.load.model.GlideUrl
import com.bumptech.glide.load.model.ModelLoader
import com.bumptech.glide.load.model.stream.BaseGlideUrlLoader
import io.reactivex.Observable
import io.reactivex.disposables.CompositeDisposable
import retrofit2.Call

import java.io.InputStream
import java.lang.Exception
import java.nio.charset.Charset
import java.security.MessageDigest
import java.util.*
import com.bumptech.glide.load.model.MultiModelLoaderFactory
import com.bumptech.glide.load.model.ModelLoaderFactory


class FourSquareImageLoader(private val repository: Repository) :
    ModelLoader<VenuesResponse.Venue, InputStream> {
    class Factory(private val repository: Repository) :
        ModelLoaderFactory<VenuesResponse.Venue, InputStream> {

        override fun build(factory: MultiModelLoaderFactory): ModelLoader<VenuesResponse.Venue, InputStream> {
            return FourSquareImageLoader(repository)
        }

        override fun teardown() {
            // No-op
        }
    }

    override fun buildLoadData(
        model: VenuesResponse.Venue,
        width: Int,
        height: Int,
        options: Options
    ): ModelLoader.LoadData<InputStream> {
        return ModelLoader.LoadData(
            VenueKey(venue = model),
            FourSquareImageFetcher(venue = model)
        )
    }


    override fun handles(venue: VenuesResponse.Venue): Boolean {
        return true
    }

    class VenueKey(val venue: VenuesResponse.Venue) : Key {
        override fun updateDiskCacheKey(messageDigest: MessageDigest) {
            messageDigest.update(venue.id.toByte())
        }

        override fun equals(other: Any?): Boolean {
            if (this === other) return true
            if (other == null || javaClass != other::javaClass) return false

            val key = other as VenuesResponse.Venue

            return venue.id == key.id
        }

        override fun hashCode(): Int {
            return venue.hashCode()
        }
    }

    inner class FourSquareImageFetcher(private val venue: VenuesResponse.Venue) :
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
