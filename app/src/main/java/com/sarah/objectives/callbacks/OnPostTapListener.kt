package com.sarah.objectives.callbacks

import com.sarah.objectives.data.posts.PostsItem


interface OnPostTapListener {
    fun onPostClicked(data: PostsItem)
}