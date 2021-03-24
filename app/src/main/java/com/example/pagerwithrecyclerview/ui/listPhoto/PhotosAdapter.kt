package com.example.pagerwithrecyclerview.ui.listPhoto

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.pagerwithrecyclerview.databinding.ItemPhotoBinding
import com.example.pagerwithrecyclerview.response.Photo

class PhotosAdapter : ListAdapter<Photo, PhotosAdapter.ViewHolder>(PhotoDiffUtils()) {

    class ViewHolder(val binding: ItemPhotoBinding) : RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PhotosAdapter.ViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        return ViewHolder(ItemPhotoBinding.inflate(layoutInflater, parent, false))
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val photo = getItem(position)
        holder.binding.let {
            Glide.with(it.ivPhoto.context).load(photo.urls?.small).into(it.ivPhoto)
        }
    }
}

class PhotoDiffUtils() : DiffUtil.ItemCallback<Photo>() {
    override fun areItemsTheSame(oldItem: Photo, newItem: Photo): Boolean {
        return oldItem == newItem
    }

    override fun areContentsTheSame(oldItem: Photo, newItem: Photo): Boolean {
        return oldItem.id == newItem.id
    }

}