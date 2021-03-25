package com.example.pagerwithrecyclerview.ui.listPhoto

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.PagerSnapHelper
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.pagerwithrecyclerview.R
import com.example.pagerwithrecyclerview.databinding.FragmentPhotosBinding
import com.example.pagerwithrecyclerview.response.PhotoResponse

class PhotosFragment : Fragment(), PhotosAdapter.PhotoClickListener {

    lateinit var binding: FragmentPhotosBinding
    private val viewModel by viewModels<PhotosViewModel>()
    lateinit var photoAdapter: PhotosAdapter
    private var listPhoto = mutableListOf<PhotoResponse>()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding =
            DataBindingUtil.inflate(layoutInflater, R.layout.fragment_photos, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel.getListPhoto()
        initAdapter()
        initRecyclerViewItemListener()

        viewModel.listPhoto.observe(viewLifecycleOwner, {
            val dummyList = mutableListOf<PhotoResponse>()
            listPhoto.addAll(it)
            dummyList.addAll(it)
            if (it.size > 0) {
                context?.let { it1 ->
                    Glide.with(it1).load(it[0].urls?.regular).into(binding.ivBackground)
                }
            }
            photoAdapter.submitList(dummyList)
        })

        viewModel.loadMoreListPhoto.observe(viewLifecycleOwner, {
            val dummyList = mutableListOf<PhotoResponse>()
            listPhoto.addAll(it)
            dummyList.addAll(listPhoto)
            photoAdapter.submitList(dummyList)
        })
    }

    private fun initAdapter() {
        photoAdapter = PhotosAdapter(this)

        binding.rvPhotos.apply {
            setItemViewCacheSize(4)
            layoutManager = PhotosAdapter.ProminentLayoutManager(context)
            adapter = photoAdapter
        }
        PagerSnapHelper().attachToRecyclerView(binding.rvPhotos)
    }

    private fun initRecyclerViewItemListener() {
        val layoutManager = binding.rvPhotos.layoutManager as LinearLayoutManager
        binding.rvPhotos.addOnScrollListener(object : RecyclerView.OnScrollListener() {
            override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
                super.onScrolled(recyclerView, dx, dy)

                val lastPosition = layoutManager.findLastCompletelyVisibleItemPosition()
                if (lastPosition == listPhoto.size - 2) viewModel.loadMoreImage()
            }

            override fun onScrollStateChanged(recyclerView: RecyclerView, newState: Int) {
                super.onScrollStateChanged(recyclerView, newState)
                if (newState == RecyclerView.SCROLL_STATE_IDLE) {
                    val firstPosition = layoutManager.findFirstCompletelyVisibleItemPosition()
                    Glide.with(context!!).load(listPhoto[firstPosition].urls?.regular)
                        .into(binding.ivBackground)
                }
            }
        })
    }

    override fun onPhotoClick(photo: PhotoResponse) {
        photo.user?.username?.let {
            findNavController().navigate(PhotosFragmentDirections.actionPhotosFragmentToPhotoDetailFragment(it))
        }

    }
}