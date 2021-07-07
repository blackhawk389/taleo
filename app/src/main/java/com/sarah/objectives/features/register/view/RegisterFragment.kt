package com.sarah.objectives.features.register.view

import android.net.Uri
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.os.bundleOf
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.google.android.material.datepicker.MaterialDatePicker
import com.sarah.objectives.R
import com.sarah.objectives.base.BaseFragment
import com.sarah.objectives.base.Resource
import com.sarah.objectives.data.user.User
import com.sarah.objectives.databinding.FragmentRegisterBinding
import com.sarah.objectives.datasource.UserRemoteDataSource
import com.sarah.objectives.features.register.viewmodel.UserViewModel
import com.sarah.objectives.repositories.UserRepository
import com.sarah.objectives.utils.*
import com.sarah.objectives.utils.ObjectivesHelper.getFileName
import dagger.hilt.android.AndroidEntryPoint
import java.io.File
import java.io.FileInputStream
import java.io.FileOutputStream
import javax.inject.Inject

@AndroidEntryPoint
class RegisterFragment : BaseFragment<FragmentRegisterBinding, UserRepository>() {

    @Inject
    lateinit var dataSource: UserRemoteDataSource
    private lateinit var viewModel: UserViewModel
    private lateinit var datePicker: MaterialDatePicker<Long>
    private var user: User? = null
    private lateinit var file: File
    private var imageUri: Uri? = null
    var isFromCamera = false
    private  var profile:File? = null


    override fun getRepository(): UserRepository = UserRepository(dataSource)

    override fun getFragmentBinding(
        inflater: LayoutInflater,
        container: ViewGroup?
    ): FragmentRegisterBinding = FragmentRegisterBinding.inflate(inflater, container, false)

    override fun onPostInit() {
        datePicker = objectiveDatePicker(R.string.date_of_birth)
        setupViewModel()
        setupData()
        routeToLoginFragment()
        setupAddressBottomSheet()
        setupProfileBottomSheet()
        setupAddress()
        setupDatePicker()
        setupProfileImage()
        setupRegisterObserver()

    }

    private fun setupData() {
        binding.registerButton.setOnClickListener {
            val name = binding.fullnameET.value()
            val email = binding.emailET.value()
            val password = binding.passwordET.value()
            val phone = binding.phoneET.value()
            val address = binding.addressET.value()
            val dob = binding.dobET.value()
            user = User(name, email, password, phone, address, dob)
            profile?.let {
                user?.profile = it
                viewModel.registerUser(user)
            }  ?: run {
                showToast(getString(R.string.profile_error))
            }
        }
    }

    private fun setupDatePicker() {
        binding.dobET.setOnClickListener {
            binding.dobET.disable()
            binding.dobET.makeNonClickable()
            datePicker.show(requireActivity().supportFragmentManager, "DOB")
        }

        datePicker.addOnPositiveButtonClickListener {
            val date = DateTimeUtils.getFormattedDateTime(it, "dd-MMM-yy")
            binding.dobET.setText(date)
            user?.dateOfBirth = date
        }
        datePicker.addOnCancelListener {
            resetDOB()
        }
        datePicker.addOnNegativeButtonClickListener {
            resetDOB()
        }

    }

    private fun resetDOB() {
        binding.dobET.clear()
        binding.dobET.enable()
        binding.dobET.makeClickable()
    }

    private fun setupAddress() {
        arguments?.let {
            val data = it["user"] as User?
            data?.let { user ->
                bindDataToViews(user)
            }
        }
    }

    private fun setupProfileImage() {
        arguments?.let {
            imageUri = it["captured_image"] as? Uri?
            val user = it["user"] as? User?
            bindDataToViews(user!!)
            binding.profileImage.setImageURI(imageUri)
            imageUri?.let { uri->
                isFromCamera = it["isFromCamera"] as Boolean
                getFile(uri,isFromCamera)?.let {
                    file = it
                    profile = file
                }
            }
        }

    }

    private fun bindDataToViews(user: User) {

        binding.dobET.value(user.dateOfBirth.toString())
        binding.emailET.value(user.email.toString())
        binding.fullnameET.value(user.fullName.toString())
        binding.addressET.value(user.address.toString())
        binding.phoneET.value(user.phone.toString())
        binding.passwordET.value(user.password.toString())

    }

    private fun setupViewModel() {
        viewModel = ViewModelProvider(this, factory).get(UserViewModel::class.java)
        binding.lifecycleOwner = this
        binding.viewModel = viewModel
    }

    private fun routeToLoginFragment() {
        binding.backArrow.setOnClickListener {
           // routeTo(R.id.action_registerFragment_to_loginFragment)
        }
    }

    private fun setupAddressBottomSheet() {
        binding.addressET.setOnClickListener {
            val name = binding.fullnameET.value()
            val email = binding.emailET.value()
            val password = binding.passwordET.value()
            val phone = binding.phoneET.value()
            val address = binding.addressET.value()
            val dob = binding.dobET.value()
            val user = User(name, email, password, phone, address, dob)
            val bundle = bundleOf("user" to user)
           // routeTo(R.id.action_registerFragment_to_addressBottomSheet, bundle)
        }

    }

    private fun setupProfileBottomSheet() {

        binding.profileImage.setOnClickListener {
            val name = binding.fullnameET.value()
            val email = binding.emailET.value()
            val password = binding.passwordET.value()
            val phone = binding.phoneET.value()
            val address = binding.addressET.value()
            val dob = binding.dobET.value()
            val user = User(name, email, password, phone, address, dob)
            val bundle = bundleOf("user" to user)
           // routeTo(R.id.action_registerFragment_to_profileBottomSheet, bundle)
        }
    }

    private fun getFile(imageUri: Uri, isFromCamera: Boolean): File? {
        val parcelFileDescriptor = requireContext().contentResolver.openFileDescriptor(
            Uri.parse(imageUri.toString()),
            "r",
            null
        )
        parcelFileDescriptor?.let {
            val inputStream = FileInputStream(parcelFileDescriptor.fileDescriptor)
            file = if (isFromCamera){
                File(imageUri.path!!)

            } else {
                File(getOutputDirectory(), requireContext().contentResolver.getFileName(imageUri))

            }
            val outputStream = FileOutputStream(file)
            inputStream.copyTo(outputStream)
        }
        return file
    }

    @Suppress("DEPRECATION")
    private fun getOutputDirectory(): File {
        val mediaDir = requireContext().externalMediaDirs.firstOrNull()?.let {
            File(it, resources.getString(R.string.app_name)).apply { mkdirs() }
        }
        return if (mediaDir != null && mediaDir.exists())
            mediaDir else requireContext().filesDir
    }

    private fun setupRegisterObserver() {
        viewModel.registerResponse.observe(viewLifecycleOwner, Observer {
            when(it.status){
                Resource.Status.LOADING -> binding.progressLoader.show()
                Resource.Status.ERROR -> {
                    binding.progressLoader.hide()
                    showToast(it.responseError!!.error)
                }
                Resource.Status.SUCCESS -> {
                    binding.progressLoader.hide()
                    showToast(it.data!!.message)
                   // routeTo(R.id.action_registerFragment_to_loginFragment)

                }
            }
        })
    }
}