package com.sarah.objectives.config.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.sarah.objectives.data.albums.AlbumItem

@Dao
interface AlbumDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(images: List<AlbumItem>)

    @Query("SELECT * FROM albums ORDER BY id")
    fun getAllAlbums(): List<AlbumItem>
}