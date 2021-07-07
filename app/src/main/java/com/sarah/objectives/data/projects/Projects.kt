package com.sarah.objectives.data.projects

data class Projects(
    val `data`: List<Data>,
    val error: String,
    val success: Boolean,
    val nextPageNo:Int
)