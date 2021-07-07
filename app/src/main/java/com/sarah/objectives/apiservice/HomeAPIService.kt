package com.sarah.objectives.apiservice

import com.sarah.objectives.data.blogs.Blogs
import com.sarah.objectives.data.photos.PhotosItem
import com.sarah.objectives.data.posts.PostsItem
import com.sarah.objectives.data.projects.Projects
import com.sarah.objectives.utils.Constants.NETWORK_CONSTANTS.PHOTOS
import com.sarah.objectives.utils.Constants.NETWORK_CONSTANTS.POSTS
import retrofit2.Response
import retrofit2.http.GET


interface HomeAPIService {

    @GET(PHOTOS)
    suspend fun getPhotos() : Response<List<PhotosItem>>

    @GET(POSTS)
    suspend fun getPosts() : Response<List<PostsItem>>
}