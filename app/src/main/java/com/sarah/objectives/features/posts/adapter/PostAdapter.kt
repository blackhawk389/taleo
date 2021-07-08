package com.sarah.objectives.features.posts.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.sarah.objectives.R
import com.sarah.objectives.callbacks.OnPostTapListener
import com.sarah.objectives.data.posts.PostsItem
import com.sarah.objectives.databinding.ItemRowsBinding
import com.sarah.objectives.utils.setColor
import kotlin.random.Random

class PostAdapter(private var listener: OnPostTapListener) : RecyclerView.Adapter<PostAdapter.PostViewHolder>() {

    private var listItems = ArrayList<PostsItem>()

    fun addPosts(items: ArrayList<PostsItem>) {
        this.listItems = items
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PostViewHolder {
        val view = ItemRowsBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return PostViewHolder(view)
    }

    override fun onBindViewHolder(holder: PostViewHolder, position: Int) {
        val items = listItems[position]
        holder.bindData(items)
    }

    override fun getItemCount(): Int = listItems.size

    inner class PostViewHolder(var itemRows: ItemRowsBinding) :
        RecyclerView.ViewHolder(itemRows.root) {

        fun bindData(items: PostsItem) {
            itemRows.posts = items
            itemRows.root.setOnClickListener {
                listener.onPostClicked(items)
            }

        }
    }

}