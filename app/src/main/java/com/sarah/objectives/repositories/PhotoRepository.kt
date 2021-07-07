package com.sarah.objectives.repositories

import com.sarah.objectives.base.BaseRepository
import com.sarah.objectives.datasource.PhotoDataSource
import javax.inject.Inject

class PhotoRepository @Inject constructor(
    private val dataSource: PhotoDataSource
) : BaseRepository() {

    suspend fun getAllPhotos() = dataSource.getAllPhotos()

}