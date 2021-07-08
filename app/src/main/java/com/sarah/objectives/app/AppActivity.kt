package com.sarah.objectives.app

import com.sarah.objectives.R
import com.sarah.objectives.base.BaseActivity
import com.sarah.objectives.databinding.ActivityAppBinding
import com.sarah.objectives.extras.EmptyRepository
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class AppActivity : BaseActivity<ActivityAppBinding, EmptyRepository>() {

    override fun getLayout(): Int = R.layout.activity_app

    override fun onPostInit() = Unit


}