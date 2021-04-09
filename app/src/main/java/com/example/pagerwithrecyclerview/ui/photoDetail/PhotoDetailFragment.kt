package com.example.pagerwithrecyclerview.ui.photoDetail

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.StaggeredGridLayoutManager
import com.bumptech.glide.Glide
import com.example.pagerwithrecyclerview.R
import com.example.pagerwithrecyclerview.databinding.FragmentPhotoDetailBinding
import com.example.pagerwithrecyclerview.response.PhotoResponse

class PhotoDetailFragment : Fragment(), View.OnClickListener {

    private lateinit var binding: FragmentPhotoDetailBinding
    private val viewModel by viewModels<PhotoDetailViewModel>()
    private val args: PhotoDetailFragmentArgs by navArgs()
    private lateinit var userId: String
    private lateinit var userPhotosAdapter: UserPhotosAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        userId = args.userId
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding =
            DataBindingUtil.inflate(
                layoutInflater,
                R.layout.fragment_photo_detail,
                container,
                false
            )

        initAdapter()

        getUserInfo(userId)
        getUserPhotos(userId, 1, 10)

        return binding.root
    }

    @SuppressLint("SetTextI18n")
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewModel.userResponse.observe(viewLifecycleOwner, { user ->
            user?.photos?.get(0)?.urls?.regular?.let { loadImage(it, binding.ivWall) }
            user?.profile_image?.large?.let { loadImage(it, binding.ivAvatar) }
            user?.username?.let { binding.tvName.text = it }
            user?.totalLike?.let { binding.tvLikeCount.text = "Total like : $it" }
            user?.totalPhoto?.let { binding.tvPhotosCount.text = "Total photos : $it" }
        })

        viewModel.listPhoto.observe(viewLifecycleOwner, { photos ->
            userPhotosAdapter.submitList(photos)
        })
    }

    private fun getUserInfo(userId: String) {
        viewModel.getUserInfo(userId)
    }

    private fun getUserPhotos(userName: String, page: Int, perPage: Int) {
        viewModel.getListPhoto(userName, page, perPage)
    }

    private fun loadImage(url: String, view: ImageView) {
        context?.let { it1 ->
            Glide.with(it1).load(url).into(view)
        }
    }

    private fun initAdapter() {
        userPhotosAdapter = UserPhotosAdapter(object : UserPhotosAdapter.PhotoClickListener {
            override fun onPhotoClick(photo: PhotoResponse) {

            }

        })

        binding.rvUserPhotos.apply {
            adapter = userPhotosAdapter
            layoutManager = StaggeredGridLayoutManager(2, LinearLayoutManager.VERTICAL)
        }
    }

    override fun onClick(v: View?) {
        when (v) {
            binding.ivBack -> findNavController().navigateUp()
        }
    }

}