package com.sarah.objectives.ui

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.test.espresso.Espresso
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.action.ViewActions.click
import androidx.test.espresso.action.ViewActions.scrollTo
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.contrib.RecyclerViewActions.actionOnItemAtPosition
import androidx.test.espresso.matcher.ViewMatchers.*
import androidx.test.filters.SmallTest
import com.sarah.objectives.R
import com.sarah.objectives.features.intro.adapter.IntroAdapter
import com.sarah.objectives.features.intro.view.IntroFragment
import com.sarah.objectives.utils.TestData.getIntroItems
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
class IntroFragmentTest {

    /**
     *  Steps
     *
     *  RecyclerView comes into view
     *  Show Lottie Animations Raw resources and text
     *  On Last Item Finish Button is visible
     *  On Click Button We will be routed to Home Fragment
     *
     * **/
    @get:Rule
    var instantExecutorRule: InstantTaskExecutorRule = InstantTaskExecutorRule()

    @Test
    fun isFragmentIntroDisplayed() {
        launchFragmentInHiltContainer<IntroFragment> {  }
        onView(withId(R.id.introFragment)).check(matches(isDisplayed()))
    }

    @Test
    fun isRecyclerViewDisplayed() {
        launchFragmentInHiltContainer<IntroFragment> {  }
        onView(withId(R.id.introRecyclerView)).check(matches(isDisplayed()))

    }

    @Test
    fun verifyRecyclerItems() {
        launchFragmentInHiltContainer<IntroFragment> {  }
        onView(withId(R.id.heading)).check(matches(withText(getIntroItems().first().title)))
        onView(withId(R.id.description)).check(matches(withText(getIntroItems().first().description)))
    }

    @Test
    fun verifyFinishButtonListeners() {
        launchFragmentInHiltContainer<IntroFragment> {  }
        onView(withId(R.id.introRecyclerView)).perform(actionOnItemAtPosition<IntroAdapter.IntroViewHolder>(2, scrollTo()))
        onView(withId(R.id.finishButton)).check(matches(isDisplayed()))
    }

    @Test
    fun verifyFinishButtonClicks()  {
        launchFragmentInHiltContainer<IntroFragment> {  }
        onView(withId(R.id.introRecyclerView)).perform(actionOnItemAtPosition<IntroAdapter.IntroViewHolder>(2, scrollTo()))
        Espresso.closeSoftKeyboard()
        onView(withId(R.id.finishButton)).perform(click())
        onView(withId(R.id.homeFragmentView)).check(matches(isDisplayed()))
    }
}