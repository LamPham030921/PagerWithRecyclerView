package com.example.pagerwithrecyclerview.ui.listPhoto

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.PagerSnapHelper
import androidx.recyclerview.widget.RecyclerView
import com.example.pagerwithrecyclerview.R
import com.example.pagerwithrecyclerview.databinding.FragmentPhotosBinding
import com.example.pagerwithrecyclerview.response.Photo

class PhotosFragment : Fragment() {

    lateinit var binding: FragmentPhotosBinding
    private val viewModel by viewModels<PhotosViewModel>()
    lateinit var photoAdapter: PhotosAdapter
    private var listPhoto = mutableListOf<Photo>()

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
            val dummyList = mutableListOf<Photo>()
            listPhoto.addAll(it)
            dummyList.addAll(it)
            photoAdapter.submitList(dummyList)
        })

        viewModel.loadMoreListPhoto.observe(viewLifecycleOwner, {
            val dummyList = mutableListOf<Photo>()
            listPhoto.addAll(it)
            dummyList.addAll(listPhoto)
            photoAdapter.submitList(dummyList)
        })
    }

    private fun initAdapter() {
        photoAdapter = PhotosAdapter()
        binding.rvPhotos.apply {
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
        })
    }
}