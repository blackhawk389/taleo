package com.sarah.objectives.features.register.view

import android.app.Activity.RESULT_OK
import android.content.Intent
import android.net.Uri
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.os.bundleOf
import com.sarah.objectives.R
import com.sarah.objectives.base.BaseBottomSheetFragment
import com.sarah.objectives.data.user.User
import com.sarah.objectives.databinding.ProfileBottomSheetLayoutBinding
import com.sarah.objectives.utils.routeTo

class ProfileBottomSheet : BaseBottomSheetFragment<ProfileBottomSheetLayoutBinding>() {


    private var user: User? = null
    private var galleryImage: Uri? = null
    private var isFromCamera = false
    override fun getFragmentBinding(
        inflater: LayoutInflater,
        container: ViewGroup?
    ): ProfileBottomSheetLayoutBinding =
        ProfileBottomSheetLayoutBinding.inflate(inflater, container, false)

    override fun onPostInit() {
        setupUserInfo()
        setupListeners()

    }

    private fun setupUserInfo() {
        arguments?.let {
            user = it["user"] as? User?
            galleryImage = it["captured_image"] as? Uri?
            galleryImage?.let { uri ->
                routeToRegisterFragment(user, uri, true)
            }
        }
    }

    private fun setupListeners() {

        binding.uploadFromGallery.setOnClickListener {
            startGalleryIntent()
        }

        binding.uploadWithCamera.setOnClickListener {
            val bundle = bundleOf("user" to user)
           // routeTo(R.id.action_profileBottomSheet_to_cameraFragment,bundle)

        }
    }

    private fun startGalleryIntent() = Intent(Intent.ACTION_PICK).apply {
        type = "image/*"
        val mimeType = arrayOf("image/jpeg", "image/jpg", "image/png")
        putExtra(Intent.EXTRA_MIME_TYPES, mimeType)
        startActivityForResult(this, REQUEST_IMAGE_CODE)
    }


    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        if (requestCode == REQUEST_IMAGE_CODE && resultCode == RESULT_OK) {
            galleryImage = data?.data
            routeToRegisterFragment(user, galleryImage,false)
        }
        super.onActivityResult(requestCode, resultCode, data)
    }

    private fun routeToRegisterFragment(user: User?, imageURI: Uri?, isFromCamera: Boolean) {
        val bundle = bundleOf("user" to user, "captured_image" to imageURI,"isFromCamera" to isFromCamera)
      //  routeTo(R.id.action_profileBottomSheet_to_registerFragment, bundle)
    }

    companion object {
        const val REQUEST_IMAGE_CODE = 1000
    }
}