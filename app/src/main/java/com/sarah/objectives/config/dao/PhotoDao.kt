package com.sarah.objectives.config.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.sarah.objectives.data.photos.PhotosItem
import com.sarah.objectives.data.projects.Data



@Dao
interface PhotoDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(images: List<PhotosItem>)

    @Query("SELECT * FROM photos ORDER BY id")
    fun getAllPhotos(): List<PhotosItem>
}