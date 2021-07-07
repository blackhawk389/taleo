package com.sarah.objectives.app

import com.sarah.objectives.R
import com.sarah.objectives.base.BaseActivity
import com.sarah.objectives.databinding.ActivityAppBinding
import com.sarah.objectives.events.LogoutEvent
import com.sarah.objectives.extras.EmptyRepository
import com.sarah.objectives.preferences.PreferenceHelper
import com.sarah.objectives.utils.showToast
import dagger.hilt.android.AndroidEntryPoint
import org.greenrobot.eventbus.Subscribe

@AndroidEntryPoint
class AppActivity : BaseActivity<ActivityAppBinding, EmptyRepository>() {

    override fun getLayout(): Int = R.layout.activity_app

    override fun onPostInit() = Unit


    @Subscribe
    fun onLogoutEvent(event:LogoutEvent) {
        PreferenceHelper.clearLoggedInPreferences()
        showToast(getString(R.string.logging_out))
        finish()
    }

}