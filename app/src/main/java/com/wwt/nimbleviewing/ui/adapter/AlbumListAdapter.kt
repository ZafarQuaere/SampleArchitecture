package com.wwt.nimbleviewing.ui.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.squareup.picasso.Picasso
import com.wwt.nimbleviewing.R
import com.wwt.nimbleviewing.data.model.Photos
import kotlinx.android.synthetic.main.list_item_album.view.*

class AlbumListAdapter(val data: MutableList<Photos>?) : RecyclerView.Adapter<AlbumViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): AlbumViewHolder =
        AlbumViewHolder(
            LayoutInflater.from(parent.context).inflate(R.layout.list_item_album, parent, false)
        )


    override fun getItemCount(): Int = data?.size ?: 0

    override fun onBindViewHolder(holder: AlbumViewHolder, position: Int) {
        data?.get(position)?.let { holder.bind(it) }
    }

    fun addData(data: List<Photos>) {
        this.data?.apply {
            clear()
            addAll(data)
        }
    }
}

class AlbumViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
    fun bind(album: Photos) {
        itemView.apply {
            text_album_title.text = album.title.filterNot { it == "e"[0] || it == "E"[0]}
            Picasso.get().load(album.url).into(album_art)
        }
    }
}

