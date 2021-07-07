package com.sarah.objectives.features.photos.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.google.gson.Gson
import com.sarah.objectives.callbacks.onPhotoClickListener
import com.sarah.objectives.data.photos.PhotosItem
import com.sarah.objectives.databinding.ItemRowsBinding
import com.sarah.objectives.databinding.PhotoRowsBinding
import com.sarah.objectives.utils.applyImage

class PhotoAdapter(private var listener: onPhotoClickListener) : RecyclerView.Adapter<PhotoAdapter.PhotoViewHolder>() {


    private val gson = Gson()
    private var listItems = ArrayList<PhotosItem>()

    fun addPhotos(items: ArrayList<PhotosItem>) {
        this.listItems = items
        notifyDataSetChanged()
    }


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PhotoViewHolder {
        val view = PhotoRowsBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return PhotoViewHolder(view)

    }

    override fun onBindViewHolder(holder: PhotoViewHolder, position: Int) {
        val items = listItems[position]
        holder.bindData(items)

    }

    override fun getItemCount(): Int = listItems.size


    inner class PhotoViewHolder(var itemRows: PhotoRowsBinding) : RecyclerView.ViewHolder(itemRows.root) {

        fun bindData(items: PhotosItem) {
            itemRows.photo.applyImage(itemRows.root.context,items.url)
            itemRows.root.setOnClickListener {
                listener.onPhotoClicked(items)
            }
        }
    }

}