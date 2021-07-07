package com.sarah.objectives.features.register.view

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.os.bundleOf
import com.sarah.objectives.R
import com.sarah.objectives.base.BaseBottomSheetFragment
import com.sarah.objectives.data.user.User
import com.sarah.objectives.databinding.AddressBottomSheetLayoutBinding
import com.sarah.objectives.utils.*
import kotlinx.android.synthetic.main.fragment_register.*

class AddressBottomSheet : BaseBottomSheetFragment<AddressBottomSheetLayoutBinding>() {

    private lateinit var fetchedAddress:String
    private  var user:User? = null
    override fun getFragmentBinding(
        inflater: LayoutInflater,
        container: ViewGroup?
    ): AddressBottomSheetLayoutBinding =
        AddressBottomSheetLayoutBinding.inflate(inflater, container, false)

    override fun onPostInit() {
        onCurrentLocation()
        onManuallyAdding()
        requireArguments()?.let {
            val userData = it["user"] as? User?
            user = userData
        }
    }

    @SuppressLint("MissingPermission")
    private fun onCurrentLocation() {
        binding.currentLocation.setOnClickListener {
            binding.addressWrapper.hide()
            fusedLocationClient.lastLocation.addOnSuccessListener {
                it?.let {
                    val addressList = geoCoder.getFromLocation(it.latitude, it.longitude, 1)
                    if (addressList.isNotEmpty()) {
                        val address = addressList.first()
                        fetchedAddress = address.getAddressLine(0)
                        user?.address = fetchedAddress

                    } else {
                        showToast(getString(R.string.unable_to_fetch_address))
                    }
                    val bundle = bundleOf("user" to user)
                  //  routeTo(R.id.action_addressBottomSheet_to_registerFragment, bundle)
                }?: run {
                    showToast(getString(R.string.manually_add_address))
                }
            }.addOnFailureListener {
                showToast(it.message.toString())
            }


        }

    }

    private fun onManuallyAdding() {
        binding.writeAddress.setOnClickListener {
            updateAddress()
        }

    }

    private fun updateAddress() {
        binding.addressWrapper.show()
        binding.updateAddressButton.show()
        binding.updateAddressButton.apply {
            setOnClickListener {
                val address = binding.addressET.value()
                if (address.isBlank()) {
                    addressET.error =   getString(R.string.address_error)
                    addressET.requestFocus()

                } else {
                    routeToRegisterFragment(address)
                }

            }
        }

    }

    private fun routeToRegisterFragment(address: String) {
        user?.address = address
        val bundle = bundleOf("user" to user)
       // routeTo(R.id.action_addressBottomSheet_to_registerFragment, bundle)
    }


}