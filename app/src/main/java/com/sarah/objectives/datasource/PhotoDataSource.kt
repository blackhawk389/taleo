package com.sarah.objectives.datasource

import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.sarah.objectives.apiservice.PhotoAPIService
import com.sarah.objectives.data.blogs.PostItems
import com.sarah.objectives.utils.Constants.STARTING_PAGE
import retrofit2.HttpException
import timber.log.Timber
import java.io.IOException
import javax.inject.Inject



class PhotoDataSource @Inject constructor(private val apiService: PhotoAPIService) : PagingSource<Int, PostItems>() {

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, PostItems> {
        val pagePosition = params.key ?: STARTING_PAGE
        return try {
            val response = apiService.getPhotos(pagePosition)
            val previousKey = if (pagePosition == STARTING_PAGE) null else pagePosition - 1
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

    override fun getRefreshKey(state: PagingState<Int, PostItems>): Int? {
        return state.anchorPosition?.let { anchorPosition ->
            state.closestPageToPosition(anchorPosition)?.prevKey?.plus(1)
                ?: state.closestPageToPosition(anchorPosition)?.nextKey?.minus(1)
        }
    }

}