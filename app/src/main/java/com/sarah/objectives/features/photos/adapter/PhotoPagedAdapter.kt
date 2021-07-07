package com.sarah.objectives.features.photos.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.sarah.objectives.callbacks.onPhotoClickListener
import com.sarah.objectives.data.photos.PhotosItem
import com.sarah.objectives.data.projects.Data
import com.sarah.objectives.databinding.ItemRowsHorizontalBinding
import com.sarah.objectives.utils.applyImage
import com.sarah.objectives.utils.value

class PhotoPagedAdapter(val onPhotoClickListener: onPhotoClickListener) : PagingDataAdapter<Data, PhotoPagedAdapter.ProjectViewHolder>(PROJECT_COMPARATOR) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ProjectViewHolder {
        val binding = ItemRowsHorizontalBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ProjectViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ProjectViewHolder, position: Int) {
        val currentItem = getItem(position)
        if (currentItem != null) {
            holder.bind(currentItem)
        }
    }

    inner class ProjectViewHolder(private val binding: ItemRowsHorizontalBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(projects: Data) {
            binding.apply {
                /*image.applyImage(binding.root.context, projects.project_image)
                description.value(projects.project_id)
                title.value(projects.project_title)
                layout.setOnClickListener {
                    onPhotoClickListener.onPhotoClicked(projects)
                }*/

            }


        }
    }

    companion object {
        private val PROJECT_COMPARATOR = object : DiffUtil.ItemCallback<Data>() {

            override fun areItemsTheSame(oldItem: Data, newItem: Data) =
                oldItem.project_id == newItem.project_id

            override fun areContentsTheSame(oldItem: Data, newItem: Data) = oldItem == newItem
        }
    }
}