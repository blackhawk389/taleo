package com.sarah.objectives.ui

import androidx.test.core.app.ActivityScenario.launch
import androidx.test.espresso.Espresso
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.matcher.ViewMatchers.isDisplayed
import androidx.test.espresso.matcher.ViewMatchers.withId
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.filters.MediumTest
import com.sarah.objectives.R
import com.sarah.objectives.app.AppActivity
import org.junit.Test
import org.junit.runner.RunWith


@MediumTest
@RunWith(AndroidJUnit4::class)
class MainActivityTest {

    @Test
    fun is_ActivityLaunched() {
        launch(AppActivity::class.java)
        Espresso.onView(withId(R.id.main)).check(matches(isDisplayed()))
    }


}