package com.sarah.objectives.datasource

import com.sarah.objectives.apiservice.HomeAPIService
import com.sarah.objectives.base.BaseResponse


class HomeDataSource(private var apiService: HomeAPIService) : BaseResponse() {

    suspend fun getRecentPosts() = getResponse {
        apiService.getPosts()
    }

    suspend fun getRecentPhotos() = getResponse {
        apiService.getPhotos()
    }
}