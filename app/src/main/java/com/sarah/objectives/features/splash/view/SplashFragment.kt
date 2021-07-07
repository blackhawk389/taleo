package com.sarah.objectives.features.splash.view

import android.os.Handler
import android.os.Looper
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.google.gson.Gson
import com.sarah.objectives.R
import com.sarah.objectives.base.BaseFragment
import com.sarah.objectives.base.Resource
import com.sarah.objectives.callbacks.DialogListener
import com.sarah.objectives.databinding.FragmentSplashBinding
import com.sarah.objectives.datasource.SplashDataSource
import com.sarah.objectives.features.splash.viewmodel.SplashViewModel
import com.sarah.objectives.preferences.PreferenceHelper
import com.sarah.objectives.repositories.SplashRepository
import com.sarah.objectives.utils.Constants.PREFERENCES.IS_LOGGED_IN
import com.sarah.objectives.utils.Constants.PREFERENCES.ON_BOARDING_FINISHED
import com.sarah.objectives.utils.routeTo
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject


@AndroidEntryPoint
class SplashFragment : BaseFragment<FragmentSplashBinding, SplashRepository>(),DialogListener {

    @Inject
    lateinit var dataSource: SplashDataSource

    @Inject
    lateinit var gson: Gson
    private var isError = false

    private lateinit var viewModel: SplashViewModel

    override fun getFragmentBinding(
        inflater: LayoutInflater,
        container: ViewGroup?
    ): FragmentSplashBinding = FragmentSplashBinding.inflate(inflater, container, false)

    override fun onPostInit() {
        initListeners(this)
        viewModel = ViewModelProvider(this, factory).get(SplashViewModel::class.java)
        /*getBlog()
        getProjects()
        getServices()*/
        setupObservers()
    }

    private fun setupObservers() {
        setupBlogObserver()
        setupProjectsObserver()
        enableScreenTimeout(3000)

    }

    override fun getRepository(): SplashRepository = SplashRepository(dataSource)

    private fun getBlog() = viewModel.getBlog()

    private fun getProjects() = viewModel.getImages()

    private fun getServices() = viewModel.getServices()

    private fun setupBlogObserver() {

        viewModel.postResponse.observe(viewLifecycleOwner, Observer {
            if (it.status == Resource.Status.SUCCESS) {
                viewModel.insertPosts(it?.data?.postItems!!)
            }
            if(it.status == Resource.Status.ERROR){
                isError  = true
            }
        })
    }

    private fun setupProjectsObserver() {
        viewModel.imageResponse.observe(viewLifecycleOwner, Observer {
            if (it.status == Resource.Status.SUCCESS) {
                viewModel.insertImages(it?.data!!.photoItems)
            }
            if(it.status == Resource.Status.ERROR){
                showDialog(getString(R.string.something_went_wrong),getString(R.string.use_offline_mode))
            }
        })
    }

    private fun enableScreenTimeout(@Suppress("SameParameterValue") millisSeconds: Long) =
        Handler(Looper.getMainLooper()).postDelayed({ handleIntents() }, millisSeconds)

    private fun handleIntents() {
        if (isOnBoardingFinished()) {
            routeTo(R.id.action_splashFragment_to_homeFragment)
        } else {
            routeTo(R.id.action_splashFragment_to_introFragment)
        }
    }

    override fun onCancel() {
        requireActivity().finishAffinity()
    }

    override fun onOkay() {
        dismiss()
    }

    private fun isOnBoardingFinished() = PreferenceHelper.getBoolean(ON_BOARDING_FINISHED)

}