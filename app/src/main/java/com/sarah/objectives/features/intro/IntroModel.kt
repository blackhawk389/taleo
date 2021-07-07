package com.sarah.objectives.features.intro

import androidx.annotation.RawRes


data class IntroModel(var title: String, var description: String, @RawRes var anim: Int)