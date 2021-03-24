package com.example.pagerwithrecyclerview.ui.listPhoto

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.pagerwithrecyclerview.R
import com.example.pagerwithrecyclerview.databinding.ItemPhotoBinding
import com.example.pagerwithrecyclerview.response.Photo

class PhotosAdapter : ListAdapter<Photo, PhotosAdapter.ViewHolder>(PhotoDiffUtils()) {

    class ViewHolder(val binding: ItemPhotoBinding) : RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        return ViewHolder(ItemPhotoBinding.inflate(layoutInflater, parent, false))
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val photo = getItem(position)
        holder.binding.ivPhoto.let {
            val recyclerHeight = it.context.resources.getDimensionPixelSize(R.dimen.recycler_height)
            val percent: Float = photo.width!!.toFloat() / photo.height!!.toFloat()
            it.layoutParams =
                ConstraintLayout.LayoutParams((recyclerHeight * percent).toInt(), recyclerHeight)
            Glide.with(it.context).load(photo.urls?.regular).into(it)
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