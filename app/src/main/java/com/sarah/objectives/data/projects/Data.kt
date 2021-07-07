package com.sarah.objectives.data.projects

import androidx.room.*
import java.io.Serializable

@Entity(tableName = "projects",indices =[Index(value = ["project_id"])])
data class Data(

    @PrimaryKey(autoGenerate = false)
    @ColumnInfo(name = "project_id")
    val project_id: Int,
    @ColumnInfo(name = "project_title")
    val project_title: String,
    @ColumnInfo(name = "project_details")
    val project_description: String,
    @ColumnInfo(name = "project_imageURL")
    val project_image: String,
    @ColumnInfo(name = "project_created")
    val createdAt: String,
    @ColumnInfo(name = "project_modified")
    val updatedAt: String
):Serializable