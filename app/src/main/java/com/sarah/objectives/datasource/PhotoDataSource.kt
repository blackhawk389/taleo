package com.sarah.objectives.datasource

import com.sarah.objectives.apiservice.PhotoAPIService
import com.sarah.objectives.base.BaseResponse
import com.sarah.objectives.config.db.ObjectiveDatabase
import com.sarah.objectives.data.posts.PostsItem
import javax.inject.Inject


class PhotoDataSource @Inject constructor(
    private val apiService: PhotoAPIService,
    private val db: ObjectiveDatabase
) : BaseResponse() {

    suspend fun getBlogFromDatabase(): List<PostsItem> = db.postDao().getAllPosts()

    suspend fun getAllPhotos() = getResponse {
        apiService.getPhotos()
    }


}