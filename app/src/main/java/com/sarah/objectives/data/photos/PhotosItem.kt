package com.sarah.objectives.data.photos

import androidx.room.Entity
import androidx.room.PrimaryKey
import java.io.Serializable

@Entity(tableName = "photos")
data class PhotosItem(
    val albumId: Int,
    @PrimaryKey
    val id: Int,
    val thumbnailUrl: String,
    val title: String,
    val url: String
):Serializable