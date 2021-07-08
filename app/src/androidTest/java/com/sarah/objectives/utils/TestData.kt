package com.sarah.objectives.utils

import com.sarah.objectives.R
import com.sarah.objectives.features.intro.IntroModel

object TestData {

    const val INTRO_LIST_ITEMS = 3
    const val DUMMY_TEXT = "Lorem ipsum dolor sit amet, consectetur adipiscing elit, sed do eiusmod tempor incididunt ut labore et dolore magna aliqua."


    fun getIntroItems(): ArrayList<IntroModel> {
        val introModelOne = IntroModel("Title No 01", DUMMY_TEXT, R.raw.people)
        val introModelTwo = IntroModel("Title No 02", DUMMY_TEXT, R.raw.teamwork)
        val introModelThree = IntroModel("Title No 03", DUMMY_TEXT, R.raw.get_a_quote)
        return arrayListOf(introModelOne, introModelTwo, introModelThree)
    }
}