package com.sarah.objectives.data.albums

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "albums")
data class AlbumItem(
    @PrimaryKey
    val id: Int,
    val title: String,
    val userId: Int
)