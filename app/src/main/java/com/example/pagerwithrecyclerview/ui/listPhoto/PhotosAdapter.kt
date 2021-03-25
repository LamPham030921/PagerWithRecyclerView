package com.example.pagerwithrecyclerview.ui.listPhoto

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.pagerwithrecyclerview.R
import com.example.pagerwithrecyclerview.databinding.ItemPhotoBinding
import com.example.pagerwithrecyclerview.response.PhotoResponse
import kotlin.math.abs
import kotlin.math.roundToInt

class PhotosAdapter(private val listener: PhotoClickListener) :
    ListAdapter<PhotoResponse, PhotosAdapter.ViewHolder>(PhotoDiffUtils()) {

    interface PhotoClickListener {
        fun onPhotoClick(photo: PhotoResponse)
    }

    class ViewHolder(val binding: ItemPhotoBinding) : RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        return ViewHolder(ItemPhotoBinding.inflate(layoutInflater, parent, false))
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val photo = getItem(position)
        holder.binding.root.setOnClickListener {
            listener.onPhotoClick(photo)
        }
        holder.binding.ivPhoto.let {
            val recyclerHeight = it.context.resources.getDimensionPixelSize(R.dimen.recycler_height)
            val percent: Float = photo.width!!.toFloat() / photo.height!!.toFloat()
            it.layoutParams =
                ConstraintLayout.LayoutParams((recyclerHeight * percent).toInt(), recyclerHeight)
            Glide.with(it.context).load(photo.urls?.regular).into(it)
        }
    }

    internal class ProminentLayoutManager(
        context: Context,

        /**
         * This value determines where items reach the final (minimum) scale:
         * - 1f is when their center is at the start/end of the RecyclerView
         * - <1f is before their center reaches the start/end of the RecyclerView
         * - >1f is outside the bounds of the RecyclerView
         * */
        private val minScaleDistanceFactor: Float = 1.5f,

        /** The final (minimum) scale for non-prominent items is 1-[scaleDownBy] */
        private val scaleDownBy: Float = 0.3f
    ) : LinearLayoutManager(context, HORIZONTAL, false) {

        override fun onLayoutCompleted(state: RecyclerView.State?) =
            super.onLayoutCompleted(state).also { scaleChildren() }

        override fun scrollHorizontallyBy(
            dx: Int,
            recycler: RecyclerView.Recycler,
            state: RecyclerView.State
        ) = super.scrollHorizontallyBy(dx, recycler, state).also {
            if (orientation == HORIZONTAL) scaleChildren()
        }

        private fun scaleChildren() {
            val containerCenter = width / 2f

            // Any view further than this threshold will be fully scaled down
            val scaleDistanceThreshold = minScaleDistanceFactor * containerCenter
            var translationXForward = 0f

            for (i in 0 until childCount) {
                val child = getChildAt(i)!!

                val childCenter = (child.left + child.right) / 2f
                val distanceToCenter = abs(childCenter - containerCenter)

                val scaleDownAmount = (distanceToCenter / scaleDistanceThreshold).coerceAtMost(1f)
                val scale = 1f - scaleDownBy * scaleDownAmount

                child.scaleX = scale
                child.scaleY = scale

                val translationDirection = if (childCenter > containerCenter) -1 else 1
                val translationXFromScale = translationDirection * child.width * (1 - scale) / 2f

                child.translationX = translationXFromScale + translationXForward

                translationXForward = 0f

                if (translationXFromScale > 0 && i >= 1) {
                    // Edit previous child
                    getChildAt(i - 1)!!.translationX += 2 * translationXFromScale

                } else if (translationXFromScale < 0) {
                    // Pass on to next child
                    translationXForward = 2 * translationXFromScale
                }
            }
        }

        override fun getExtraLayoutSpace(state: RecyclerView.State): Int {
            // Since we're scaling down items, we need to pre-load more of them offscreen.
            // The value is sort of empirical: the more we scale down, the more extra space we need.
            return (width / (1 - scaleDownBy)).roundToInt()
        }
    }

    class PhotoDiffUtils() : DiffUtil.ItemCallback<PhotoResponse>() {
        override fun areItemsTheSame(oldItem: PhotoResponse, newItem: PhotoResponse): Boolean {
            return oldItem == newItem
        }

        override fun areContentsTheSame(oldItem: PhotoResponse, newItem: PhotoResponse): Boolean {
            return oldItem.id == newItem.id
        }

    }
}