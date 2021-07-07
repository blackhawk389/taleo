package com.sarah.objectives.base

import android.location.Geocoder
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.viewbinding.ViewBinding
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationServices
import com.google.android.material.bottomsheet.BottomSheetDialogFragment


abstract class BaseBottomSheetFragment<B : ViewBinding> : BottomSheetDialogFragment() {

    protected lateinit var binding: B
    protected lateinit var fusedLocationClient: FusedLocationProviderClient
    protected lateinit var geoCoder:Geocoder

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding =  getFragmentBinding(inflater,container)
        return binding.root
    }

    abstract fun getFragmentBinding(inflater: LayoutInflater, container: ViewGroup?): B

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        fusedLocationClient = LocationServices.getFusedLocationProviderClient(requireContext())
        geoCoder = Geocoder(requireContext())
        onPostInit()
    }

    abstract fun onPostInit()
}