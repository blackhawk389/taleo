package com.sarah.objectives.ui

import android.os.Handler
import android.os.Looper
import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.action.ViewActions
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.contrib.RecyclerViewActions.actionOnItemAtPosition
import androidx.test.espresso.matcher.ViewMatchers.*
import androidx.test.filters.SmallTest
import com.sarah.objectives.R
import com.sarah.objectives.features.home.view.HomeFragment
import com.sarah.objectives.features.photos.adapter.PhotoAdapter
import com.sarah.objectives.features.posts.adapter.PostAdapter
import com.sarah.objectives.utils.launchFragmentInHiltContainer
import com.sarah.objectives.utils.withDrawable
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
class HomeFragmentTest {

    @get:Rule
    var instantExecutorRule: InstantTaskExecutorRule = InstantTaskExecutorRule()

    @Test
    fun isFragmentIntroDisplayed() {
        launchFragmentInHiltContainer<HomeFragment> { }
        onView(withId(R.id.homeFragmentView)).check(matches(isDisplayed()))
    }

    @Test
    fun verifyName() {
        launchFragmentInHiltContainer<HomeFragment> { }
        onView(withId(R.id.headerText)).check(matches(withText("Hi Sarah")))
    }

    @Test
    fun verifyDetailsText() {
        launchFragmentInHiltContainer<HomeFragment> { }
        onView(withId(R.id.detailsText)).check(matches(withText(R.string.objective_2021)))
    }

    @Test
    fun verifyPostsHeading() {
        launchFragmentInHiltContainer<HomeFragment> { }
        onView(withId(R.id.recentBlogsTitle)).check(matches(withText(R.string.posts)))
    }

    @Test
    fun verifySeeAllPostsText() {
        launchFragmentInHiltContainer<HomeFragment> { }
        onView(withId(R.id.see_all_blogs)).check(matches(withText(R.string.see_all)))
    }

    @Test
    fun verifyPhotosHeading() {
        launchFragmentInHiltContainer<HomeFragment> { }
        onView(withId(R.id.recentProjectTitle)).check(matches(withText(R.string.photos)))
    }

    @Test
    fun verifySeeAllPhotosText() {
        launchFragmentInHiltContainer<HomeFragment> { }
        onView(withId(R.id.see_all_projects)).check(matches(withText(R.string.see_all)))
    }


    @Test
    fun isPostRecyclerViewDisplayed() {
        launchFragmentInHiltContainer<HomeFragment> {}
        Handler(Looper.getMainLooper()).postDelayed({
            onView(withId(R.id.recentBlogsRecyclerView)).check(matches(isDisplayed()))
        },3000)

    }

    @Test
    fun isPhotoRecyclerViewDisplayed() {
        launchFragmentInHiltContainer<HomeFragment> { }
        Handler(Looper.getMainLooper()).postDelayed({
            onView(withId(R.id.recentProjectsRecyclerView)).check(matches(isDisplayed()))
        },3000)

    }

    @Test
    fun verifyPostItems() {
        launchFragmentInHiltContainer<HomeFragment> { }
        Handler(Looper.getMainLooper()).postDelayed({
            onView(withId(R.id.recentBlogsRecyclerView)).check(matches(isDisplayed()))
            onView(withId(R.id.title)).check(matches(withText("")))
            onView(withId(R.id.userID)).check(matches(withText("")))
        },9000)

    }

    @Test
    fun verifyPhotoItems() {
        launchFragmentInHiltContainer<HomeFragment> { }
        Handler(Looper.getMainLooper()).postDelayed({
            onView(withId(R.id.recentProjectsRecyclerView)).check(matches(isDisplayed()))
            onView(withId(R.id.photo)).check(matches(withDrawable(R.drawable.placeholder)))
        },9000)

    }

    @Test
    fun verifyOnPostClicked() {
        launchFragmentInHiltContainer<HomeFragment> { }
        Handler(Looper.getMainLooper()).postDelayed({
            onView(withId(R.id.recentBlogsRecyclerView)).check(matches(isDisplayed()))
            onView(withId(R.id.introRecyclerView)).perform(actionOnItemAtPosition<PostAdapter.PostViewHolder>(1, ViewActions.click()))
            onView(withId(R.id.postDetailView)).check(matches(isDisplayed()))


        },9000)

    }

    @Test
    fun verifyOnPhotoClicked() {
        launchFragmentInHiltContainer<HomeFragment> { }
        Handler(Looper.getMainLooper()).postDelayed({
            onView(withId(R.id.recentProjectsRecyclerView)).check(matches(isDisplayed()))
            onView(withId(R.id.introRecyclerView)).perform(actionOnItemAtPosition<PhotoAdapter.PhotoViewHolder>(1, ViewActions.click()))
            onView(withId(R.id.photoDetailsView)).check(matches(isDisplayed()))
        },9000)
    }

    @Test
    fun onSeeAllPostsClicked() {
        launchFragmentInHiltContainer<HomeFragment> { }
        Handler(Looper.getMainLooper()).postDelayed({
            onView(withId(R.id.see_all_blogs)).perform(ViewActions.click())
            onView(withId(R.id.allPostsView)).check(matches(isDisplayed()))

        },9000)

    }

    @Test
    fun onSeeAllPhotoClicked() {
        launchFragmentInHiltContainer<HomeFragment> { }
        Handler(Looper.getMainLooper()).postDelayed({
            onView(withId(R.id.see_all_projects)).perform(ViewActions.click())
            onView(withId(R.id.allPhotosView)).check(matches(isDisplayed()))

        },9000)

    }

}