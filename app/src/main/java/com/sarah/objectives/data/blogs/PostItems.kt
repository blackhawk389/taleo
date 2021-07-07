package com.sarah.objectives.data.blogs

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.Index
import androidx.room.PrimaryKey
import java.io.Serializable

@Entity(tableName = "blogs",indices = [Index(value = ["blog_id"])])
data class PostItems(
    @PrimaryKey(autoGenerate = false)
    @ColumnInfo(name = "blog_id")
    val blog_id: Int,
    @ColumnInfo(name = "blog_title")
    val blog_title: String,
    @ColumnInfo(name = "blog_subtitle")
    val blog_sub_title: String,
    @ColumnInfo(name = "blog_details")
    val blog_description: String,
    @ColumnInfo(name = "blog_imageURL")
    val blog_header_image: String,
    @ColumnInfo(name = "blog_created")
    val createdAt: String,
    @ColumnInfo(name = "blog_modified")
    val updatedAt: String
):Serializable