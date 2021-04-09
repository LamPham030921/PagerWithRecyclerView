package com.example.pagerwithrecyclerview.ui.photoDetail

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.pagerwithrecyclerview.R
import com.example.pagerwithrecyclerview.databinding.ItemUserPhotoBinding
import com.example.pagerwithrecyclerview.response.PhotoResponse
import com.example.pagerwithrecyclerview.ui.listPhoto.PhotosAdapter

class UserPhotosAdapter(private val listener: PhotoClickListener) :
    ListAdapter<PhotoResponse, UserPhotosAdapter.ViewHolder>(PhotosAdapter.PhotoDiffUtils()) {

    interface PhotoClickListener {
        fun onPhotoClick(photo: PhotoResponse)
    }

    class ViewHolder(val binding: ItemUserPhotoBinding) : RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        return ViewHolder(ItemUserPhotoBinding.inflate(layoutInflater, parent, false))
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val photo = getItem(position)
        holder.binding.root.setOnClickListener {
            listener.onPhotoClick(photo)
        }
        holder.binding.ivPhoto.let {
            val recyclerHeight =
                it.context.resources.getDimensionPixelSize(R.dimen.recycler_height)
            val percent: Float = photo.width!!.toFloat() / photo.height!!.toFloat()
            val param = it.layoutParams

            param.height = recyclerHeight
            param.width = (recyclerHeight * percent).toInt()
            it.layoutParams = param

            Glide.with(it.context).load(photo.urls?.regular).into(it)
        }
    }
}