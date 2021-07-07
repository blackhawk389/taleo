package com.sarah.objectives.features.posts.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.sarah.objectives.callbacks.OnPostTapListener
import com.sarah.objectives.data.blogs.PostItems
import com.sarah.objectives.databinding.ItemRowsHorizontalBinding

class PostPagedAdapter(val onPostTapListener: OnPostTapListener): PagingDataAdapter<PostItems, PostPagedAdapter.PostViewHolder>(POST_COMPARATOR) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PostViewHolder {
        val binding = ItemRowsHorizontalBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return PostViewHolder(binding)
    }

    override fun onBindViewHolder(holder: PostViewHolder, position: Int) {
        val currentItem = getItem(position)

        if (currentItem != null) {
            holder.bind(currentItem)
        }
    }

    inner class PostViewHolder(private val binding: ItemRowsHorizontalBinding) : RecyclerView.ViewHolder(binding.root) {

        fun bind(blog: PostItems) {
            /*binding.apply {
                image.applyImage(binding.root.context,blog.blog_header_image)
                description.value(blog.blog_sub_title)
                title.value(blog.blog_title)
                layout.setOnClickListener {
                    onPostTapListener.onPostClicked(blog)
                }

            }*/


        }
    }

    companion object {
        private val POST_COMPARATOR = object : DiffUtil.ItemCallback<PostItems>() {

            override fun areItemsTheSame(oldItem: PostItems, newItem: PostItems) = oldItem.blog_id==newItem.blog_id

            override fun areContentsTheSame(oldItem: PostItems, newItem: PostItems) = oldItem == newItem
        }
    }
}