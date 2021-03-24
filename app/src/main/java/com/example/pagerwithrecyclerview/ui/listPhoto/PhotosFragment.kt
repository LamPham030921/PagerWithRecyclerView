package com.example.pagerwithrecyclerview.ui.listPhoto

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.pagerwithrecyclerview.R
import com.example.pagerwithrecyclerview.databinding.FragmentPhotosBinding

class PhotosFragment : Fragment() {

    lateinit var binding: FragmentPhotosBinding
    private val viewModel by viewModels<PhotosViewModel>()
    lateinit var photoAdapter: PhotosAdapter

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

        viewModel.listPhoto.observe(viewLifecycleOwner, {
            photoAdapter.submitList(it)
        })
    }

    private fun initAdapter() {
        photoAdapter = PhotosAdapter()
        binding.rvPhotos.apply {
            layoutManager = LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false)
            adapter = photoAdapter
        }
    }
}