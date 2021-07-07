package com.sarah.objectives.features.posts.view

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.os.bundleOf
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import com.sarah.objectives.R
import com.sarah.objectives.base.BaseFragment
import com.sarah.objectives.callbacks.OnPostTapListener
import com.sarah.objectives.data.posts.PostsItem
import com.sarah.objectives.databinding.FragmentAllBlogBinding
import com.sarah.objectives.datasource.BlogDataSource
import com.sarah.objectives.datasource.PhotoDataSource
import com.sarah.objectives.features.posts.adapter.PostPagedAdapter
import com.sarah.objectives.features.posts.viewmodel.PostViewModel
import com.sarah.objectives.repositories.PostRepository
import com.sarah.objectives.utils.hide
import com.sarah.objectives.utils.init
import com.sarah.objectives.utils.routeTo
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import javax.inject.Inject

@AndroidEntryPoint
class AllPostFragment : BaseFragment<FragmentAllBlogBinding, PostRepository>() ,OnPostTapListener{

    private lateinit var viewModel: PostViewModel
    private lateinit var postPagedAdapter: PostPagedAdapter

    @Inject
    lateinit var pagingDataSource:PhotoDataSource
    @Inject
    lateinit var dataSource: BlogDataSource

    override fun getRepository(): PostRepository = PostRepository(
        dataSource,
        pagingDataSource
    )

    override fun getFragmentBinding(
        inflater: LayoutInflater,
        container: ViewGroup?
    ): FragmentAllBlogBinding = FragmentAllBlogBinding.inflate(inflater, container, false)

    override fun onPostInit() {
        setupViewModel()
        setupAdapter()
        setupData()
    }

    private fun setupData() {
        lifecycleScope.launch {
            viewModel.getPaginatedBlog()?.let {
                it.collectLatest {
                    binding.progressBar.hide()
                    postPagedAdapter.submitData(it)
                }
            }
        }
        setupRecyclerView()
    }
    private fun setupAdapter() {
        postPagedAdapter = PostPagedAdapter(this)
    }

    private fun setupViewModel() {
        viewModel = ViewModelProvider(this, factory).get(PostViewModel::class.java)
    }


    private fun setupRecyclerView() {
        binding.allBlogRecyclerView.init(requireContext(),postPagedAdapter)

    }

    override fun onPostClicked(data: PostsItem) {

        routeTo(R.id.action_allBlogFragment_to_blogDetailFragment, bundleOf("blog" to data))
    }
}