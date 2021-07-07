package com.sarah.objectives.features.photos.view

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.os.bundleOf
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import com.sarah.objectives.R
import com.sarah.objectives.base.BaseFragment
import com.sarah.objectives.callbacks.onPhotoClickListener
import com.sarah.objectives.data.photos.PhotosItem
import com.sarah.objectives.databinding.FragmentAllProjectsBinding
import com.sarah.objectives.datasource.PostDataSource
import com.sarah.objectives.datasource.PhotoPagedDataSource
import com.sarah.objectives.features.photos.adapter.PhotoPagedAdapter
import com.sarah.objectives.features.photos.viewmodel.PhotoViewModel
import com.sarah.objectives.repositories.PhotoRepository
import com.sarah.objectives.utils.hide
import com.sarah.objectives.utils.init
import com.sarah.objectives.utils.routeTo
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import javax.inject.Inject

@AndroidEntryPoint
class AllPhotosFragment : BaseFragment<FragmentAllProjectsBinding, PhotoRepository>(),
    onPhotoClickListener {

    private lateinit var viewModel: PhotoViewModel
    private lateinit var photoAdapter: PhotoPagedAdapter

    @Inject
    lateinit var dataSource: PostDataSource

    @Inject
    lateinit var pagedDataDataSource: PhotoPagedDataSource

    override fun getFragmentBinding(
        inflater: LayoutInflater,
        container: ViewGroup?
    ): FragmentAllProjectsBinding = FragmentAllProjectsBinding.inflate(inflater, container, false)

    override fun getRepository(): PhotoRepository = PhotoRepository(dataSource,pagedDataDataSource)

    override fun onPostInit() {
        setupViewModel()
        setupAdapter()
        setupData()
    }

    private fun setupAdapter() {
        photoAdapter = PhotoPagedAdapter(this)
    }

    private fun setupViewModel() {
        viewModel = ViewModelProvider(this, factory).get(PhotoViewModel::class.java)
    }

    override fun onPhotoClicked(data: PhotosItem) {
        routeTo(R.id.action_allProjectFragment_to_projectDetailsFragment, bundleOf("project" to data))

    }

    private fun setupData() {
        lifecycleScope.launch {
            viewModel.getPaginatedPhotos()?.let {
                it.collectLatest {
                    binding.progressBar.hide()
                    photoAdapter.submitData(it)
                }
            }
        }
        setupRecyclerView()

    }

    private fun setupRecyclerView() {
        binding.allProjectRV.init(requireContext(), photoAdapter)

    }
}