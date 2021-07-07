package com.sarah.objectives.worker

import android.content.Context
import androidx.work.CoroutineWorker
import androidx.work.WorkerParameters
import com.google.gson.Gson
import com.sarah.objectives.apiservice.HomeAPIService
import com.sarah.objectives.config.db.ObjectiveDatabase
import com.sarah.objectives.config.network.NetworkClient
import com.sarah.objectives.data.photos.PhotosItem
import com.sarah.objectives.data.posts.PostsItem
import retrofit2.Retrofit
import timber.log.Timber
import java.lang.Exception


class PhotoWorker(context: Context, workerParameters: WorkerParameters) :
    CoroutineWorker(context, workerParameters) {

    private var isSuccessful = false
    private lateinit var retrofit: Retrofit
    private var db = ObjectiveDatabase(context)

    override suspend fun doWork(): Result {
        retrofit = NetworkClient.getInstance(Gson())
        return if (isTaskSuccessful()) {
            Result.success()
        } else {
            Result.retry()
        }
    }

    private suspend fun isTaskSuccessful(): Boolean {

        val service = getAPIService()
        return if (service.getPhotos().isSuccessful) {
            isSuccessful = true
            val data = service.getPhotos().body()
            try {
                data?.let {
                    db.photoDao().insert(it as ArrayList<PhotosItem>)
                }
            } catch (e: Exception) {
                Timber.e(e)
            }
            isSuccessful

        } else {
            isSuccessful
        }


    }

    private fun getAPIService() = retrofit.create(HomeAPIService::class.java)
}