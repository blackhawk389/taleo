package com.sarah.objectives.features.home.view

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.os.bundleOf
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.android.gms.ads.AdRequest
import com.google.gson.Gson
import com.sarah.objectives.R
import com.sarah.objectives.base.BaseFragment
import com.sarah.objectives.base.Resource
import com.sarah.objectives.callbacks.OnPostTapListener
import com.sarah.objectives.callbacks.ServiceListener
import com.sarah.objectives.callbacks.onPhotoClickListener
import com.sarah.objectives.data.photos.PhotosItem
import com.sarah.objectives.data.posts.PostsItem
import com.sarah.objectives.databinding.FragmentHomeBinding
import com.sarah.objectives.datasource.HomeDataSource
import com.sarah.objectives.features.home.viewmodel.HomeViewModel
import com.sarah.objectives.features.photos.adapter.PhotoAdapter
import com.sarah.objectives.features.posts.adapter.PostAdapter
import com.sarah.objectives.features.services.adapter.ServiceAdapter
import com.sarah.objectives.features.services.model.Services
import com.sarah.objectives.preferences.PreferenceHelper
import com.sarah.objectives.repositories.HomeRepository
import com.sarah.objectives.utils.routeTo
import com.sarah.objectives.utils.showToast
import com.sarah.objectives.utils.value
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class HomeFragment : BaseFragment<FragmentHomeBinding, HomeRepository>(), ServiceListener, OnPostTapListener, onPhotoClickListener {

    private lateinit var serviceAdapter: ServiceAdapter
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
        binding.headerText.value(getString(R.string.username,"Sarah"))
        setupAds()
        setupViewModel()
        setupAdapter()
        setupServices()
        setupPosts()
        setupPhotos()
        routeToAllBlog()
        routeToAllProjects()
        setupLogoutListeners()
        setupRecyclerViews()
    }

    private fun setupAds() {
        val adRequest = AdRequest.Builder().build()
        binding.adView.loadAd(adRequest)
    }

    private fun setupLogoutListeners() {
        binding.logout.setOnClickListener {
            PreferenceHelper.clearLoggedInPreferences()
            showToast(getString(R.string.logging_out))
        }
    }
    private fun setupAdapter() {
        serviceAdapter = ServiceAdapter(this)
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
            layoutManager =
                LinearLayoutManager(requireContext(), LinearLayoutManager.HORIZONTAL, false)
            adapter = postAdapter

        }
    }

    private fun setupViewModel() {
        viewModel = ViewModelProvider(this, factory)[HomeViewModel::class.java]
        binding.lifecycleOwner = this
    }

    private fun setupServices() {
        serviceAdapter.addServices(getServices())
        binding.servicesRecyclerView.apply {
            layoutManager =
                LinearLayoutManager(requireContext(), LinearLayoutManager.HORIZONTAL, false)
            adapter = serviceAdapter
        }
    }

    private fun getServices() = arrayListOf(
        Services(
            R.drawable.ic_camera,
            getString(R.string.design_consultancy),
            getString(R.string.design_consultancy_desc)
        ),
        Services(
            R.drawable.ic_address,
            getString(R.string.interior_design_deco),
            getString(R.string.interior_desc)
        ),
        Services(
            R.drawable.ic_address,
            getString(R.string.turnkey_solution),
            getString(R.string.turnkey_desc)
        ),
        Services(
            R.drawable.ic_address,
            getString(R.string.gray_structure),
            getString(R.string.gray_structure_desc)
        ),
        Services(
            R.drawable.ic_address,
            getString(R.string.house_renovation),
            getString(R.string.renovation_desc)
        ),
        Services(
            R.drawable.ic_address,
            getString(R.string.real_estate),
            getString(R.string.real_estate_desc)
        )
    )


    override fun onServiceClicked(data: Services) {
        routeTo(R.id.action_homeFragment_to_serviceDetailFragment, bundleOf("service" to data))
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