package com.sarah.objectives.features.photos.view

import android.view.LayoutInflater
import android.view.ViewGroup
import com.google.gson.Gson
import com.sarah.objectives.base.BaseFragment
import com.sarah.objectives.data.photos.PhotosItem
import com.sarah.objectives.databinding.FragmentPhotoDetailsBinding
import com.sarah.objectives.extras.EmptyRepository
import com.sarah.objectives.utils.applyImage
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class PhotoDetailsFragment : BaseFragment<FragmentPhotoDetailsBinding, EmptyRepository>() {

    @Inject
    lateinit var gson: Gson
    private lateinit var photos: PhotosItem

    override fun getRepository(): EmptyRepository = EmptyRepository()

    override fun getFragmentBinding(
        inflater: LayoutInflater,
        container: ViewGroup?
    ): FragmentPhotoDetailsBinding = FragmentPhotoDetailsBinding.inflate(inflater, container, false)

    override fun onPostInit() {
        setupProjectDetails()
    }

    private fun setupProjectDetails() {
        arguments?.let {
            photos = it["photos"] as PhotosItem
        }
        binding.photoUrl.applyImage(requireContext(), photos.url)

    }
}