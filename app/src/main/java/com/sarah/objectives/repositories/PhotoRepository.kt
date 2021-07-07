package com.sarah.objectives.repositories

import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import com.sarah.objectives.base.BaseRepository
import com.sarah.objectives.data.photos.PhotosItem
import com.sarah.objectives.datasource.PostDataSource
import com.sarah.objectives.datasource.PhotoPagedDataSource
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class PhotoRepository @Inject constructor(
    private val dataSource: PostDataSource,
    private val pagingDataDataSource: PhotoPagedDataSource
) : BaseRepository() {

    suspend fun getProjects() = dataSource.getProjects()


    fun getPaginatedPhotos(): Flow<PagingData<PhotosItem>> = Pager(
        config = PagingConfig(
            pageSize = 10,
            initialLoadSize = 10,
            prefetchDistance = 10,
            enablePlaceholders = false
        ),
        pagingSourceFactory = { pagingDataDataSource }
    ).flow

}