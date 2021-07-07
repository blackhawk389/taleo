package com.sarah.objectives.data.posts

import com.google.gson.annotations.SerializedName

data class Posts(@SerializedName("postItems") val postItems: ArrayList<PostsItem>)