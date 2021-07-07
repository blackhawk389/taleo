package com.sarah.objectives.config.db

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.sarah.objectives.config.dao.PostDao
import com.sarah.objectives.config.dao.PhotoDao
import com.sarah.objectives.data.photos.PhotosItem
import com.sarah.objectives.data.posts.PostsItem
import com.sarah.objectives.utils.Constants.DATABASE.DATABASE_NAME
import com.sarah.objectives.utils.Constants.DATABASE.VERSION



@Database(
    entities = [PostsItem::class, PhotosItem::class],
    version = VERSION,
    exportSchema = false
)

abstract class ObjectiveDatabase : RoomDatabase() {

    abstract fun postDao(): PostDao
    abstract fun photoDao(): PhotoDao

    companion object {
        @Volatile
        private var instance: ObjectiveDatabase? = null
        private val LOCK = Any()

        operator fun invoke(context: Context) = instance ?: synchronized(LOCK) {
            instance ?: buildDatabase(context).also { instance = it }
        }

        private fun buildDatabase(context: Context) = Room.databaseBuilder(
            context,
            ObjectiveDatabase::class.java, DATABASE_NAME
        ).build()
    }

}
