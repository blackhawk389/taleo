package com.sarah.objectives.features.photos.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import androidx.paging.cachedIn
import com.sarah.objectives.data.photos.PhotosItem
import com.sarah.objectives.data.projects.Data
import com.sarah.objectives.repositories.PhotoRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class PhotoViewModel @Inject constructor(private val repository: PhotoRepository) : ViewModel() {

    /*  private var _projectList = MutableLiveData<List<Data>>()
      val projectList: LiveData<List<Data>> get() = _projectList*/

/*    fun requestProjects() {
        viewModelScope.launch {
            val allProjects:List<Data>
            withContext(Dispatchers.IO) {
                allProjects = repository.getProjects()
            }
            _projectList.value = allProjects
        }
    }*/

    private var currentSearchResult: Flow<PagingData<PhotosItem>>? = null

    fun getPaginatedPhotos(): Flow<PagingData<PhotosItem>>? {

        val newResult: Flow<PagingData<PhotosItem>> = repository.getPaginatedPhotos()
            .cachedIn(viewModelScope)
        currentSearchResult = newResult
        return currentSearchResult

    }
}