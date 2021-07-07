package com.sarah.objectives.repositories

import com.sarah.objectives.base.BaseRepository
import com.sarah.objectives.base.Resource
import com.sarah.objectives.data.photos.PhotosItem
import com.sarah.objectives.data.posts.Posts
import com.sarah.objectives.data.posts.PostsItem
import com.sarah.objectives.datasource.SplashDataSource
import javax.inject.Inject

class SplashRepository @Inject constructor(private val dataSource: SplashDataSource) : BaseRepository() {

    suspend fun getPosts(): Resource<Posts> = dataSource.getPosts()

    suspend fun getPhotos() = dataSource.getPhotos()

    suspend fun getServices() = dataSource.getServices()

    suspend fun insertPosts(posts: ArrayList<PostsItem>) = dataSource.insertPosts(posts)

    suspend fun insertPhotos(images: ArrayList<PhotosItem>) = dataSource.addImages(images)
}