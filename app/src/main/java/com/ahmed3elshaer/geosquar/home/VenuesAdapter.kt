/*
 * *
 *  * Created by Ahmed Elshaer on 10/26/19 5:52 AM
 *  * Copyright (c) 2019 . All rights reserved.
 *  * Last modified 10/26/19 5:52 AM
 *
 */

package com.ahmed3elshaer.geosquar.home

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.ahmed3elshaer.geosquar.R
import com.ahmed3elshaer.geosquar.common.loader.GlideApp
import com.ahmed3elshaer.geosquar.common.models.Venue
import kotlinx.android.synthetic.main.venue_item.view.*

class VenuesAdapter : RecyclerView.Adapter<VenuesAdapter.VenuesViewHolder>() {
    private val currentList = mutableListOf<Venue>()

    fun updateList(list: List<Venue>) {
        currentList.clear()
        currentList.addAll(list)
        notifyDataSetChanged()
    }

    override fun getItemCount(): Int {
        return currentList.size
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): VenuesViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        return VenuesViewHolder(inflater.inflate(R.layout.venue_item, parent, false))
    }

    override fun onBindViewHolder(holder: VenuesViewHolder, position: Int) {
        holder.bind(currentList[position])
    }

    class VenuesViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val context = itemView.context
        fun bind(venue: Venue) = with(itemView) {

            GlideApp.with(context)
                    .load(venue)
                    .into(image_venue)

            text_name.text = venue.name
            text_address.text = venue.location.address
        }
    }

    class VenueDiffCallback : DiffUtil.ItemCallback<Venue>() {
        override fun areItemsTheSame(oldItem: Venue, newItem: Venue): Boolean {
            return oldItem.id == oldItem.id
        }

        override fun areContentsTheSame(oldItem: Venue, newItem: Venue): Boolean {
            return oldItem == oldItem
        }
    }
}
