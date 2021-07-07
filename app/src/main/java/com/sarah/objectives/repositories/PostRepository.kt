package com.sarah.objectives.repositories

import com.sarah.objectives.base.BaseRepository
import com.sarah.objectives.base.Resource
import com.sarah.objectives.data.posts.PostsItem
import com.sarah.objectives.datasource.PostDataSource
import javax.inject.Inject


class PostRepository @Inject constructor(
    private val dataSource: PostDataSource
) : BaseRepository() {

    suspend fun getAllPosts(): Resource<ArrayList<PostsItem>> = dataSource.getAllPosts()
}