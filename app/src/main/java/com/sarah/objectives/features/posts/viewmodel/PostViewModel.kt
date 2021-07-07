package com.sarah.objectives.features.posts.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import androidx.paging.cachedIn
import com.sarah.objectives.data.blogs.PostItems
import com.sarah.objectives.repositories.PostRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class PostViewModel @Inject constructor(private val repository: PostRepository) : ViewModel() {


    private var currentSearchResult: Flow<PagingData<PostItems>>? = null


    fun getPaginatedBlog(): Flow<PagingData<PostItems>>? {

        val newResult: Flow<PagingData<PostItems>> = repository.getPaginatedBlog()
            .cachedIn(viewModelScope)
        currentSearchResult = newResult
        return currentSearchResult

    }


}