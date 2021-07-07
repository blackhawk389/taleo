package com.sarah.objectives.data.posts

import androidx.room.Entity
import androidx.room.PrimaryKey
import java.io.Serializable

@Entity(tableName = "posts")
data class PostsItem(
    val body: String,
    @PrimaryKey
    val id: Int,
    val title: String,
    val userId: Int
):Serializable