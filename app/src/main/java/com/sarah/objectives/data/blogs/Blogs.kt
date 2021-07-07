package com.sarah.objectives.data.blogs

data class Blogs(
    val `data`: List<PostItems>,
    val error: String,
    val success: Boolean,
    val nextPageNo:Int
)