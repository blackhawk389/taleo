package com.sarah.objectives.features.photos.view

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.os.bundleOf
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.sarah.objectives.R
import com.sarah.objectives.base.BaseFragment
import com.sarah.objectives.base.Resource
import com.sarah.objectives.callbacks.onPhotoClickListener
import com.sarah.objectives.data.photos.PhotosItem
import com.sarah.objectives.databinding.FragmentAllPhotosBinding
import com.sarah.objectives.datasource.PhotoDataSource
import com.sarah.objectives.features.photos.adapter.PhotoAdapter
import com.sarah.objectives.features.photos.viewmodel.PhotoViewModel
import com.sarah.objectives.repositories.PhotoRepository
import com.sarah.objectives.utils.*
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class AllPhotosFragment : BaseFragment<FragmentAllPhotosBinding, PhotoRepository>(),
    onPhotoClickListener {

    private lateinit var viewModel: PhotoViewModel
    private lateinit var photoAdapter: PhotoAdapter

    @Inject
    lateinit var dataSource: PhotoDataSource


    override fun getFragmentBinding(
        inflater: LayoutInflater,
        container: ViewGroup?
    ) = FragmentAllPhotosBinding.inflate(inflater, container, false)

    override fun getRepository(): PhotoRepository = PhotoRepository(dataSource)

    override fun onPostInit() {
        setupViewModel()
        setupAdapter()
        setupData()
    }

    private fun setupAdapter() {
        photoAdapter = PhotoAdapter(this)
    }

    private fun setupViewModel() {
        viewModel = ViewModelProvider(this, factory).get(PhotoViewModel::class.java)
    }

    override fun onPhotoClicked(data: PhotosItem) {
        routeTo(
            R.id.action_allProjectFragment_to_projectDetailsFragment,
            bundleOf("photos" to data)
        )

    }

    private fun setupData() {
        viewModel.getAllPhotos()
        viewModel.allPhotos.observe(viewLifecycleOwner, Observer {
            when (it.status) {
                Resource.Status.LOADING -> {
                    binding.progressBar.show()
                }
                Resource.Status.SUCCESS -> {
                    binding.progressBar.hide()
                    photoAdapter.addPhotos(it.data!!)
                }
                Resource.Status.ERROR -> {
                    binding.progressBar.show()
                    showToast(it.responseError!!.error)
                }
            }
        })
        setupRecyclerView()

    }

    private fun setupRecyclerView() {
        binding.allProjectRV.init(requireContext(), photoAdapter)

    }
}