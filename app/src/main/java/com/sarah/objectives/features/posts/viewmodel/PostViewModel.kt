package com.sarah.objectives.features.posts.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.sarah.objectives.base.Resource
import com.sarah.objectives.data.posts.PostsItem
import com.sarah.objectives.repositories.PostRepository
import kotlinx.coroutines.launch
import javax.inject.Inject

class PostViewModel @Inject constructor(private val repository: PostRepository) : ViewModel() {


    private var _allPosts  = MutableLiveData<Resource<ArrayList<PostsItem>>>()
    val allPosts = _allPosts


    fun getAllPosts() {
        viewModelScope.launch {
            _allPosts.value = repository.getAllPosts()
        }
    }


}