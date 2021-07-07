package com.sarah.objectives.repositories

import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import com.sarah.objectives.base.BaseRepository
import com.sarah.objectives.data.blogs.PostItems
import com.sarah.objectives.datasource.BlogDataSource
import com.sarah.objectives.datasource.PhotoDataSource
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject


class PostRepository @Inject constructor(
    private val dataSource: BlogDataSource,
    private val pagingDataSource: PhotoDataSource
) : BaseRepository() {

  /*  suspend fun getBlog() = dataSource.getBlogFromDatabase()*/

    fun getPaginatedBlog(): Flow<PagingData<PostItems>> = Pager(
        config = PagingConfig(
            pageSize = 10,
            initialLoadSize = 10,
            prefetchDistance = 10,
            enablePlaceholders = false
        ),
        pagingSourceFactory = { pagingDataSource }
    ).flow

}