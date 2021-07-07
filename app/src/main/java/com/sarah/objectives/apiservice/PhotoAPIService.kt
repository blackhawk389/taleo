package com.sarah.objectives.apiservice

import com.sarah.objectives.data.blogs.Blogs
import retrofit2.http.GET
import retrofit2.http.Path


interface PhotoAPIService {

    @GET("photos")
    suspend fun getPhotos(@Path("pageNo") position: Int):Blogs
}