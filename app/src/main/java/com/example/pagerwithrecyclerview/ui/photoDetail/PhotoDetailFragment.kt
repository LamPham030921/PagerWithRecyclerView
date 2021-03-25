package com.example.pagerwithrecyclerview.ui.photoDetail

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
import com.bumptech.glide.Glide
import com.example.pagerwithrecyclerview.R
import com.example.pagerwithrecyclerview.databinding.FragmentPhotoDetailBinding

class PhotoDetailFragment : Fragment(), View.OnClickListener {

    private lateinit var binding: FragmentPhotoDetailBinding
    private val viewModel by viewModels<PhotoDetailViewModel>()
    private val args: PhotoDetailFragmentArgs by navArgs()
    private lateinit var userId: String

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

        getUserInfo(userId)

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewModel.userResponse.observe(viewLifecycleOwner, { user ->
            user?.photos?.get(0)?.urls?.regular?.let { loadImage(it, binding.ivWall) }
            user?.profile_image?.large?.let { loadImage(it, binding.ivAvatar) }
            user?.username?.let { binding.tvName.text = it }
        })

    }

    private fun getUserInfo(userId: String) {
        viewModel.getUserInfo(userId)
    }

    private fun loadImage(url: String, view: ImageView) {
        context?.let { it1 ->
            Glide.with(it1).load(url).into(view)
        }
    }

    override fun onClick(v: View?) {
        when(v){
            binding.ivBack -> findNavController().navigateUp()
        }
    }

}