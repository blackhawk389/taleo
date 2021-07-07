package com.sarah.objectives.features.services.view

import android.view.LayoutInflater
import android.view.ViewGroup
import com.sarah.objectives.base.BaseFragment
import com.sarah.objectives.databinding.FragmentServiceDetailsBinding
import com.sarah.objectives.extras.EmptyRepository
import com.sarah.objectives.features.services.model.Services

class ServiceDetailFragment:BaseFragment<FragmentServiceDetailsBinding,EmptyRepository>() {

    private lateinit var services:Services

    override fun getFragmentBinding(
        inflater: LayoutInflater,
        container: ViewGroup?
    ): FragmentServiceDetailsBinding  = FragmentServiceDetailsBinding.inflate(inflater,container,false)

    override fun getRepository(): EmptyRepository  = EmptyRepository()

    override fun onPostInit() {
        getServices()
    }

    private fun getServices() {
        arguments?.let {
            services =  it["service"] as Services
        }
        setupData(services)
    }

    private fun setupData(services: Services) {
        binding.services = services
    }
}