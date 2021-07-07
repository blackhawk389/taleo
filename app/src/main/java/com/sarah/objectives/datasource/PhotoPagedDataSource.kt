package com.sarah.objectives.datasource

import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.sarah.objectives.apiservice.PostAPIService
import com.sarah.objectives.data.projects.Data
import com.sarah.objectives.utils.Constants
import retrofit2.HttpException
import timber.log.Timber
import java.io.IOException
import javax.inject.Inject


class PhotoPagedDataSource @Inject constructor(private val apiService: PostAPIService) : PagingSource<Int, Data>() {

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, Data> {
        val pagePosition = params.key ?: Constants.STARTING_PAGE
        return try {
            val response = apiService.getPaginatedProjects(pagePosition)
            val previousKey = if (pagePosition == Constants.STARTING_PAGE) null else pagePosition - 1
            if (response.data.isNotEmpty()){
                LoadResult.Page(
                    data = response.data,
                    prevKey = previousKey,
                    nextKey = response.nextPageNo
                )
            } else {
                LoadResult.Page(
                    data = response.data,
                    prevKey = previousKey,
                    nextKey = null
                )
            }

        } catch (exception: IOException) {
            Timber.e(exception)
            return LoadResult.Error(exception)
        } catch (exception: HttpException) {
            Timber.e(exception)
            return LoadResult.Error(exception)
        }

    }

    override fun getRefreshKey(state: PagingState<Int, Data>): Int? {
        return state.anchorPosition?.let { anchorPosition ->
            state.closestPageToPosition(anchorPosition)?.prevKey?.plus(1)
                ?: state.closestPageToPosition(anchorPosition)?.nextKey?.minus(1)
        }
    }

}
