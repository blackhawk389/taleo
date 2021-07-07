package com.sarah.objectives.utils

import android.content.Context
import androidx.room.Room
import com.sarah.objectives.config.db.ObjectiveDatabase
import org.mockito.Mockito

fun getDatabase(context: Context): ObjectiveDatabase = Room.inMemoryDatabaseBuilder(context, ObjectiveDatabase::class.java).build()

fun getBlogDao(database: ObjectiveDatabase) = database.postDao()

fun getProjectDao(database: ObjectiveDatabase) = database.photoDao()

fun <T> anyValue(): T = Mockito.any<T>()