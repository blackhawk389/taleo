package com.sarah.objectives.config.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.sarah.objectives.data.posts.PostsItem


@Dao
interface PostDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(blogs: ArrayList<PostsItem>)

    @Query("SELECT * FROM posts ORDER BY id DESC")
    fun getAllPosts(): List<PostsItem>
}