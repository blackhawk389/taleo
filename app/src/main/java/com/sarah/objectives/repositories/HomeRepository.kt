package com.sarah.objectives.repositories

import com.sarah.objectives.base.BaseRepository
import com.sarah.objectives.base.Resource
import com.sarah.objectives.data.photos.PhotosItem
import com.sarah.objectives.data.posts.PostsItem
import com.sarah.objectives.data.projects.Projects
import com.sarah.objectives.datasource.HomeDataSource

class HomeRepository(private var homeDataSource: HomeDataSource) : BaseRepository() {

    suspend fun getRecentPosts():Resource<ArrayList<PostsItem>> = homeDataSource.getRecentPosts() as Resource<ArrayList<PostsItem>>
    suspend fun getPhotoItems():Resource<ArrayList<PhotosItem>> = homeDataSource.getRecentPhotos()  as Resource<ArrayList<PhotosItem>>
}