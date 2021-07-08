package com.sarah.objectives.features.home.view

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.os.bundleOf
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.gson.Gson
import com.sarah.objectives.R
import com.sarah.objectives.base.BaseFragment
import com.sarah.objectives.base.Resource
import com.sarah.objectives.callbacks.OnPostTapListener
import com.sarah.objectives.callbacks.onPhotoClickListener
import com.sarah.objectives.data.photos.PhotosItem
import com.sarah.objectives.data.posts.PostsItem
import com.sarah.objectives.databinding.FragmentHomeBinding
import com.sarah.objectives.datasource.HomeDataSource
import com.sarah.objectives.features.home.viewmodel.HomeViewModel
import com.sarah.objectives.features.photos.adapter.PhotoAdapter
import com.sarah.objectives.features.posts.adapter.PostAdapter
import com.sarah.objectives.repositories.HomeRepository
import com.sarah.objectives.utils.routeTo
import com.sarah.objectives.utils.showToast
import com.sarah.objectives.utils.value
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class HomeFragment : BaseFragment<FragmentHomeBinding, HomeRepository>(), OnPostTapListener, onPhotoClickListener {

    private lateinit var viewModel: HomeViewModel
    private lateinit var postAdapter: PostAdapter
    private lateinit var photoAdapter: PhotoAdapter

    @Inject
    lateinit var dataSource: HomeDataSource

    @Inject
    lateinit var gson: Gson

    override fun getRepository(): HomeRepository = HomeRepository(dataSource)

    override fun getFragmentBinding(
        inflater: LayoutInflater,
        container: ViewGroup?
    ): FragmentHomeBinding = FragmentHomeBinding.inflate(inflater, container, false)

    override fun onPostInit() {
        binding.headerText.value(getString(R.string.username, "Sarah"))
        setupViewModel()
        setupAdapter()
        setupPosts()
        setupPhotos()
        routeToAllBlog()
        routeToAllProjects()
        setupRecyclerViews()
    }


    private fun setupAdapter() {
        postAdapter = PostAdapter(this)
        photoAdapter = PhotoAdapter(this)

    }

    private fun setupPosts() {
        viewModel.requestPosts()
        viewModel.posts.observe(viewLifecycleOwner, Observer {
            if (it.status == Resource.Status.SUCCESS) {
                postAdapter.addPosts(it.data!!)
                hideProgressLoader(binding.progressBar)

            }
            if (it.status == Resource.Status.LOADING) {
                showProgressLoader(binding.progressBar)

            }
            if (it.status == Resource.Status.ERROR) {
                hideProgressLoader(binding.progressBar)
                showToast(it.message.toString())
            }

        })
    }

    private fun setupRecyclerViews() {
        setupPostRecyclerView()
        setupPhotosRecyclerView()
    }

    private fun setupPhotos() {
        viewModel.requestImages()
        viewModel.photoItems.observe(viewLifecycleOwner, Observer {
            if (it.status == Resource.Status.SUCCESS) {
                photoAdapter.addPhotos(it.data!!)
                hideProgressLoader(binding.progressBar)

            }
            if (it.status == Resource.Status.LOADING) {
                showProgressLoader(binding.progressBar)

            }
            if (it.status == Resource.Status.ERROR) {
                hideProgressLoader(binding.progressBar)
                showToast(it.message.toString())
            }
        })
    }

    private fun setupPhotosRecyclerView() {
        binding.recentProjectsRecyclerView.apply {
            layoutManager =
                LinearLayoutManager(requireContext(), LinearLayoutManager.HORIZONTAL, false)
            adapter = photoAdapter

        }
    }


    private fun setupPostRecyclerView() {
        binding.recentBlogsRecyclerView.apply {
            layoutManager = LinearLayoutManager(requireContext())
            adapter = postAdapter
        }
    }

    private fun setupViewModel() {
        viewModel = ViewModelProvider(this, factory)[HomeViewModel::class.java]
        binding.lifecycleOwner = this
    }


    override fun onPostClicked(data: PostsItem) {
        routeTo(R.id.action_home_to_post_details, bundleOf("posts" to data))
    }

    override fun onPhotoClicked(data: PhotosItem) {
        routeTo(R.id.action_homeFragment_to_projectDetailsFragment, bundleOf("photos" to data))

    }

    private fun routeToAllBlog() {
        binding.seeAllBlogs.setOnClickListener {
            routeTo(R.id.action_homeFragment_to_allBlogFragment)
        }
    }

    private fun routeToAllProjects() {
        binding.seeAllProjects.setOnClickListener {
            routeTo(R.id.action_homeFragment_to_allProjectFragment)
        }

    }
}