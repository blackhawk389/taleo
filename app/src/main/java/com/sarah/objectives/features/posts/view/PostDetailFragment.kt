package com.sarah.objectives.features.posts.view

import android.view.LayoutInflater
import android.view.ViewGroup
import com.sarah.objectives.base.BaseFragment
import com.sarah.objectives.data.posts.PostsItem
import com.sarah.objectives.databinding.FragmentBlogDetailBinding
import com.sarah.objectives.extras.EmptyRepository
import com.sarah.objectives.utils.applyImage
import dagger.hilt.android.AndroidEntryPoint


@AndroidEntryPoint
class PostDetailFragment : BaseFragment<FragmentBlogDetailBinding, EmptyRepository>() {

    override fun getFragmentBinding(
        inflater: LayoutInflater,
        container: ViewGroup?
    ): FragmentBlogDetailBinding = FragmentBlogDetailBinding.inflate(inflater, container, false)

    override fun getRepository(): EmptyRepository = EmptyRepository()

    override fun onPostInit() {
        setupData()
    }

    private fun setupData() {
        binding.blogImage.applyImage(requireContext(),"")
        getPosts()?.let {
            binding.posts = it
        }
    }

    private fun getPosts(): PostsItem? {
        arguments?.let {
            return it["posts"] as PostsItem
        }
        return null
    }
}