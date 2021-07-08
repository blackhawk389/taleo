package com.sarah.objectives.features.posts.view

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.os.bundleOf
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.sarah.objectives.R
import com.sarah.objectives.base.BaseFragment
import com.sarah.objectives.base.Resource
import com.sarah.objectives.callbacks.OnPostTapListener
import com.sarah.objectives.data.posts.PostsItem
import com.sarah.objectives.databinding.FragmentAllPostsBinding
import com.sarah.objectives.datasource.PostDataSource
import com.sarah.objectives.features.posts.adapter.PostAdapter
import com.sarah.objectives.features.posts.viewmodel.PostViewModel
import com.sarah.objectives.repositories.PostRepository
import com.sarah.objectives.utils.*
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class AllPostFragment : BaseFragment<FragmentAllPostsBinding, PostRepository>() ,OnPostTapListener{

    private lateinit var viewModel: PostViewModel
    private lateinit var postPagedAdapter: PostAdapter

    @Inject
    lateinit var dataSource: PostDataSource

    override fun getRepository(): PostRepository = PostRepository(dataSource)

    override fun getFragmentBinding(
        inflater: LayoutInflater,
        container: ViewGroup?
    ): FragmentAllPostsBinding = FragmentAllPostsBinding.inflate(inflater, container, false)

    override fun onPostInit() {
        setupViewModel()
        setupAdapter()
        setupData()
    }

    private fun setupData() {
        viewModel.getAllPosts()
        viewModel.allPosts.observe(viewLifecycleOwner, Observer {
            when(it.status) {
                Resource.Status.LOADING -> binding.progressBar.show()
                Resource.Status.SUCCESS -> {
                    binding.progressBar.hide()
                    postPagedAdapter.addPosts(it.data!!)
                }
                Resource.Status.ERROR -> {
                    binding.progressBar.hide()
                    showToast(it.responseError!!.error)
                }
            }
        })
        setupRecyclerView()
    }
    private fun setupAdapter() {
        postPagedAdapter = PostAdapter(this)
    }

    private fun setupViewModel() {
        viewModel = ViewModelProvider(this, factory).get(PostViewModel::class.java)
    }


    private fun setupRecyclerView() {
        binding.allBlogRecyclerView.init(requireContext(),postPagedAdapter)

    }

    override fun onPostClicked(data: PostsItem) {

        routeTo(R.id.action_allBlogFragment_to_blogDetailFragment, bundleOf("posts" to data))
    }
}