package com.sarah.objectives.features.home.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.sarah.objectives.base.Resource
import com.sarah.objectives.data.photos.PhotosItem
import com.sarah.objectives.data.posts.PostsItem
import com.sarah.objectives.repositories.HomeRepository
import kotlinx.coroutines.launch

class HomeViewModel(private var repository: HomeRepository) : ViewModel() {

    private var _posts = MutableLiveData<Resource<ArrayList<PostsItem>>>()

    val posts = _posts

    private var _photoItems = MutableLiveData<Resource<ArrayList<PhotosItem>>>()

    val photoItems = _photoItems

    fun requestPosts() {
        viewModelScope.launch {
            _posts.value = repository.getRecentPosts()
        }
    }

    fun requestImages() {
        viewModelScope.launch {
            _photoItems.value = repository.getPhotoItems()
        }
    }
}