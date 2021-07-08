package com.sarah.objectives.features.intro.view

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import com.sarah.objectives.R
import com.sarah.objectives.base.BaseFragment
import com.sarah.objectives.callbacks.IntroListener
import com.sarah.objectives.databinding.FragmentIntroBinding
import com.sarah.objectives.extras.EmptyRepository
import com.sarah.objectives.features.intro.IntroModel
import com.sarah.objectives.features.intro.adapter.IntroAdapter
import com.sarah.objectives.preferences.PreferenceHelper
import com.sarah.objectives.utils.Constants.PREFERENCES.ON_BOARDING_FINISHED
import com.sarah.objectives.utils.routeTo

class IntroFragment : BaseFragment<FragmentIntroBinding, EmptyRepository>(), IntroListener {

    private lateinit var introAdapter: IntroAdapter
    override fun getFragmentBinding(
        inflater: LayoutInflater,
        container: ViewGroup?
    ): FragmentIntroBinding = FragmentIntroBinding.inflate(inflater, container, false)

    override fun onPostInit() {
        introAdapter = IntroAdapter(this)
        setupRecyclerView()
    }

    override fun getRepository(): EmptyRepository = EmptyRepository()

    private fun setupRecyclerView() {
        introAdapter.addIntroItems(setupData())
        binding.introRecyclerView.apply {
            adapter = introAdapter
            layoutManager = LinearLayoutManager(requireContext(), LinearLayoutManager.HORIZONTAL, false)
        }
    }

    private fun setupData(): ArrayList<IntroModel> {
        val introModelOne = IntroModel(getString(R.string.intro_posts), getString(R.string.dummy_text), R.raw.post)
        val introModelTwo = IntroModel(getString(R.string.intro_image), getString(R.string.dummy_text), R.raw.image_02)
        val introModelThree = IntroModel(getString(R.string.intro_fun), getString(R.string.dummy_text), R.raw.image_01)
        return arrayListOf(introModelOne, introModelTwo, introModelThree)
    }

    override fun onFinished() {
        PreferenceHelper.putBoolean(ON_BOARDING_FINISHED,true)
        routeTo(R.id.action_introFragment_to_homeFragment)
    }
}