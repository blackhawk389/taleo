package com.sarah.objectives.features.services.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.sarah.objectives.callbacks.ServiceListener
import com.sarah.objectives.databinding.ServiceRowsBinding
import com.sarah.objectives.features.services.model.Services
import com.sarah.objectives.utils.value


class ServiceAdapter(private var serviceListener: ServiceListener) :
    RecyclerView.Adapter<ServiceAdapter.ServiceViewHolder>() {

    private var introItems = ArrayList<Services>()
    fun addServices(items: ArrayList<Services>) {
        this.introItems = items
    }


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ServiceViewHolder {
        val view = ServiceRowsBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ServiceViewHolder(view)

    }

    override fun onBindViewHolder(holder: ServiceViewHolder, position: Int) {
        val items = introItems[position]
        holder.bindData(items)

    }

    override fun getItemCount(): Int = introItems.size


    inner class ServiceViewHolder(var itemRows: ServiceRowsBinding) :
        RecyclerView.ViewHolder(itemRows.root) {

        fun bindData(items: Services) {

            itemRows.serviceImage.setImageResource(items.serviceImage)
            itemRows.serviceTitle.value(items.serviceName)
            itemRows.serviceLayout.setOnClickListener {
                serviceListener.onServiceClicked(items)
            }
        }


    }

}