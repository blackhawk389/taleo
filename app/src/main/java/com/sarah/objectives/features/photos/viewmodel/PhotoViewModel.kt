package com.sarah.objectives.features.photos.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.sarah.objectives.base.Resource
import com.sarah.objectives.data.photos.PhotosItem
import com.sarah.objectives.repositories.PhotoRepository
import kotlinx.coroutines.launch
import javax.inject.Inject

class PhotoViewModel @Inject constructor(private val repository: PhotoRepository) : ViewModel() {


    private var _allPhotos = MutableLiveData<Resource<ArrayList<PhotosItem>>>()

    val allPhotos = _allPhotos

    fun getAllPhotos() {
        viewModelScope.launch {
            _allPhotos.value = repository.getAllPhotos()
        }
    }
}