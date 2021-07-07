package com.sarah.objectives.datasource

import com.sarah.objectives.apiservice.PostAPIService
import com.sarah.objectives.base.BaseResponse
import com.sarah.objectives.config.db.ObjectiveDatabase
import javax.inject.Inject


class PostDataSource @Inject constructor(
    private val apiService: PostAPIService,
    private val database: ObjectiveDatabase
) : BaseResponse() {

    suspend fun getProjects() = database.photoDao().getAllPhotos()
}