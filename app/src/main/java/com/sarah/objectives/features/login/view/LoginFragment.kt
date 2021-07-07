package com.sarah.objectives.features.login.view

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.google.gson.Gson
import com.sarah.objectives.R
import com.sarah.objectives.base.BaseFragment
import com.sarah.objectives.base.Resource
import com.sarah.objectives.databinding.FragmentLoginBinding
import com.sarah.objectives.datasource.UserRemoteDataSource
import com.sarah.objectives.features.register.viewmodel.UserViewModel
import com.sarah.objectives.preferences.PreferenceHelper
import com.sarah.objectives.repositories.UserRepository
import com.sarah.objectives.utils.Constants.PREFERENCES.IS_LOGGED_IN
import com.sarah.objectives.utils.Constants.PREFERENCES.USER_DETAILS
import com.sarah.objectives.utils.hide
import com.sarah.objectives.utils.routeTo
import com.sarah.objectives.utils.show
import com.sarah.objectives.utils.showToast
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class LoginFragment : BaseFragment<FragmentLoginBinding, UserRepository>() {

    @Inject
    lateinit var dataSource: UserRemoteDataSource
    private lateinit var viewModel: UserViewModel

    @Inject
    lateinit var gson: Gson

    override fun getRepository(): UserRepository = UserRepository(dataSource)

    override fun getFragmentBinding(
        inflater: LayoutInflater,
        container: ViewGroup?
    ): FragmentLoginBinding = FragmentLoginBinding.inflate(inflater, container, false)

    override fun onPostInit() {
        setupViewModel()
        routeToRegisterFragment()
        setupListeners()
        setupLoginObserver()
        setupUserInfoObserver()

    }

    private fun setupListeners() {
        binding.loginButton.setOnClickListener {
            viewModel.enqueueTokenRequest()
        }
    }

    private fun setupLoginObserver() {
        viewModel.tokenResponse.observe(viewLifecycleOwner, Observer {
            when (it.status) {
                Resource.Status.SUCCESS -> {
                    binding.progressLoader.hide()
                    PreferenceHelper.putToken(it.data?.data!!.token)
                    viewModel.getUserInfo()
                }
                Resource.Status.ERROR -> {
                    binding.progressLoader.hide()
                    showToast(it.responseError!!.error)
                }
                Resource.Status.LOADING -> binding.progressLoader.show()
            }
        })
    }

    private fun setupViewModel() {
        viewModel = ViewModelProvider(this, factory).get(UserViewModel::class.java)
        binding.lifecycleOwner = this
        binding.viewModel = viewModel
    }

    private fun routeToRegisterFragment() {
        binding.registerTextView.setOnClickListener {
        //    routeTo(R.id.action_loginFragment_to_registerFragment)
        }
    }

    private fun setupUserInfoObserver() {
        viewModel.userInfo.observe(viewLifecycleOwner, Observer {
            when (it.status) {
                Resource.Status.SUCCESS -> {
                    val userJson = gson.toJson(it.data?.data)
                    PreferenceHelper.putString(USER_DETAILS, userJson)
                    binding.progressLoader.hide()
                    PreferenceHelper.putBoolean(IS_LOGGED_IN, true)
                  //  routeTo(R.id.action_loginFragment_to_homeFragment)
                }
                Resource.Status.ERROR -> {
                    binding.progressLoader.hide()
                    showToast(it.data?.error!!)
                }
                Resource.Status.LOADING -> binding.progressLoader.show()

            }
        })
    }
}