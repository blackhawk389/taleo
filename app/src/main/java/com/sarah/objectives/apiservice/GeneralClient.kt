package com.sarah.objectives.apiservice

import com.sarah.objectives.data.photos.Photos
import com.sarah.objectives.data.posts.Posts
import com.sarah.objectives.data.services.Services
import com.sarah.objectives.utils.Constants.NETWORK_CONSTANTS.PHOTOS
import com.sarah.objectives.utils.Constants.NETWORK_CONSTANTS.POSTS
import com.sarah.objectives.utils.Constants.NETWORK_CONSTANTS.SERVICES_ENDPOINT
import retrofit2.Response
import retrofit2.http.GET


interface GeneralClient {

    //getting just page 1  for now

    @GET(POSTS)
    suspend fun getPosts(): Response<Posts>

    @GET(PHOTOS)
    suspend fun getPhotos(): Response<Photos>

}