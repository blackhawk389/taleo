package com.sarah.objectives.apiservice

import com.sarah.objectives.data.photos.PhotosItem
import retrofit2.Response
import retrofit2.http.GET


interface PhotoAPIService {

    @GET("photos")
    suspend fun getPhotos():Response<ArrayList<PhotosItem>>
}