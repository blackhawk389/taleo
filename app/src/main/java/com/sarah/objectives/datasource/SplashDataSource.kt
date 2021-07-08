package com.sarah.objectives.datasource

import com.sarah.objectives.apiservice.GeneralClient
import com.sarah.objectives.base.BaseResponse
import com.sarah.objectives.base.Resource
import com.sarah.objectives.config.db.ObjectiveDatabase
import com.sarah.objectives.data.photos.PhotosItem
import com.sarah.objectives.data.posts.Posts
import com.sarah.objectives.data.posts.PostsItem
import javax.inject.Inject



class SplashDataSource @Inject constructor(
    private val apiService: GeneralClient,
    private var database: ObjectiveDatabase
) : BaseResponse() {


    suspend fun getPosts(): Resource<Posts> = getResponse {
        apiService.getPosts()
    }

    suspend fun getPhotos() = getResponse {
        apiService.getPhotos()
    }

    suspend fun insertPosts(data: ArrayList<PostsItem>){
        database.postDao().insert(data)
    }

    suspend fun addImages(data: ArrayList<PhotosItem>){
        database.photoDao().insert(data)
    }
}