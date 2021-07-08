package com.sarah.objectives.ui

import android.os.Handler
import android.os.Looper
import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.test.core.app.ActivityScenario
import androidx.test.espresso.Espresso
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.matcher.ViewMatchers.*
import androidx.test.filters.SmallTest
import com.sarah.objectives.HiltTestActivity
import com.sarah.objectives.R
import com.sarah.objectives.features.splash.view.SplashFragment
import com.sarah.objectives.utils.launchFragmentInHiltContainer
import dagger.hilt.android.testing.HiltAndroidTest
import dagger.hilt.android.testing.HiltTestApplication
import kotlinx.coroutines.ExperimentalCoroutinesApi
import org.junit.Rule
import org.junit.Test
import org.robolectric.annotation.Config

@ExperimentalCoroutinesApi
@SmallTest
@HiltAndroidTest
@Config(application = HiltTestApplication::class)
class SplashFragmentTest {

    @get:Rule
    var instantExecutorRule: InstantTaskExecutorRule = InstantTaskExecutorRule()
    @Test
    fun verifySplashTitle() {
        ActivityScenario.launch(HiltTestActivity::class.java)
        launchFragmentInHiltContainer<SplashFragment> {}
        Espresso.onView(withId(R.id.name)).check(matches(withText("Objective")))

    }

    @Test
    fun verifySplashSlogan() {
        ActivityScenario.launch(HiltTestActivity::class.java)
        launchFragmentInHiltContainer<SplashFragment>{}
        Espresso.onView(withId(R.id.sub_heading)).check(matches(withText("Android-2021")))

    }

    @Test
    fun verifyNavigationToIntroFragment() {
        ActivityScenario.launch(HiltTestActivity::class.java)
        launchFragmentInHiltContainer<SplashFragment> {  }
        Handler(Looper.getMainLooper()).postDelayed({
            Espresso.onView(withId(R.id.introFragment)).check(matches(isDisplayed()))
        },3000)

    }
}