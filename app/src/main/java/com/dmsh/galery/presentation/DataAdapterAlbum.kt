package com.dmsh.galery.presentation

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.dmsh.domain.entities.AlbumItem
import com.dmsh.galery.R
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.item_list_album.view.*

class DataAdapterAlbum(
    private val context: Context,
    private var albumResponseItems: List<AlbumItem>?
    ) : RecyclerView.Adapter<DataAdapterAlbum.AlbumViewHolder>() {


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): AlbumViewHolder {
        return AlbumViewHolder(LayoutInflater.from(context)
            .inflate(R.layout.item_list_album, parent, false))
    }

    override fun onBindViewHolder(albumViewHolder: AlbumViewHolder, position: Int) {
        with(albumResponseItems?.get(position)){
            albumViewHolder.itemView.textViewName.text = this?.title
            Picasso.get().load(this?.url).into(albumViewHolder.itemView.imageView)
        }
    }

    override fun getItemCount(): Int = albumResponseItems?.size ?: 0

    inner class AlbumViewHolder(itemListAlbumView: View) : RecyclerView.ViewHolder(itemListAlbumView)
}