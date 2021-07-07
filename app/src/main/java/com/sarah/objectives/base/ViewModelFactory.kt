package com.sarah.objectives.base

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.sarah.objectives.features.posts.viewmodel.PostViewModel
import com.sarah.objectives.features.home.viewmodel.HomeViewModel
import com.sarah.objectives.features.photos.viewmodel.PhotoViewModel
import com.sarah.objectives.features.register.viewmodel.UserViewModel
import com.sarah.objectives.features.splash.viewmodel.SplashViewModel
import com.sarah.objectives.repositories.*
import com.sarah.objectives.utils.Constants.UNKNOWN_VIEWMODEL


class ViewModelFactory(private val repository: BaseRepository) : ViewModelProvider.Factory {

    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        return when {
            modelClass.isAssignableFrom(SplashViewModel::class.java) -> SplashViewModel(repository as SplashRepository) as T
            modelClass.isAssignableFrom(UserViewModel::class.java) -> UserViewModel(repository as UserRepository) as T
            modelClass.isAssignableFrom(HomeViewModel::class.java) -> HomeViewModel(repository as HomeRepository) as T
            modelClass.isAssignableFrom(PostViewModel::class.java) -> PostViewModel(repository as PostRepository) as T
            modelClass.isAssignableFrom(PhotoViewModel::class.java) -> PhotoViewModel(repository as PhotoRepository) as T
            else -> throw IllegalAccessException(UNKNOWN_VIEWMODEL)
        }
    }
}