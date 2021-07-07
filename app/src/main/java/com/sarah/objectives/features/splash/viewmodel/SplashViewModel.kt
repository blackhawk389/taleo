package com.sarah.objectives.features.splash.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.sarah.objectives.base.Resource
import com.sarah.objectives.data.photos.Photos
import com.sarah.objectives.data.photos.PhotosItem
import com.sarah.objectives.data.posts.Posts
import com.sarah.objectives.data.posts.PostsItem
import com.sarah.objectives.data.services.Services
import com.sarah.objectives.repositories.SplashRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext


class SplashViewModel constructor(private val repository: SplashRepository) : ViewModel() {

    private var _postsResponse = MutableLiveData<Resource<Posts>>()

    val postResponse = _postsResponse

    private var _imageResponse = MutableLiveData<Resource<Photos>>()

    val imageResponse = _imageResponse


    fun getBlog() {
        viewModelScope.launch {
            _postsResponse.value = repository.getPosts()
        }

    }

    fun getImages() {
        viewModelScope.launch {
            _imageResponse.value = repository.getPhotos()

        }
    }


    fun insertPosts(posts: ArrayList<PostsItem>) {
        viewModelScope.launch {
            withContext(Dispatchers.IO) {
                repository.insertPosts(posts)
            }
        }
    }

    fun insertImages(images: ArrayList<PhotosItem>) {
        viewModelScope.launch {
            withContext(Dispatchers.IO) {
                repository.insertPhotos(images)
            }
        }
    }
}