package com.sarah.objectives.apiservice

import com.sarah.objectives.data.posts.PostsItem
import com.sarah.objectives.utils.Constants.NETWORK_CONSTANTS.POSTS
import retrofit2.Response
import retrofit2.http.GET


interface PostAPIService {

    @GET(POSTS)
    suspend fun getAllPosts(): Response<ArrayList<PostsItem>>
}