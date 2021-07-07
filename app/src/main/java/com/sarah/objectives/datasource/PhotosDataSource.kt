package com.sarah.objectives.datasource

import com.sarah.objectives.apiservice.PhotoAPIService
import com.sarah.objectives.base.BaseResponse
import com.sarah.objectives.config.db.ObjectiveDatabase
import javax.inject.Inject


class PhotosDataSource @Inject constructor(
    private val apiService: PhotoAPIService,
    private val database: ObjectiveDatabase
) : BaseResponse() {

    suspend fun getProjects() = database.photoDao().getAllPhotos()
}