package com.sarah.objectives.app

import android.annotation.SuppressLint
import android.app.Application
import android.content.Context
import androidx.work.*
import com.sarah.objectives.BuildConfig
import com.sarah.objectives.preferences.PreferenceHelper
import com.sarah.objectives.utils.Constants.WORKER.TAG_PHOTOS
import com.sarah.objectives.utils.Constants.WORKER.TAG_POST
import com.sarah.objectives.worker.PhotoWorker
import com.sarah.objectives.worker.PostWorker
import dagger.hilt.android.HiltAndroidApp
import timber.log.Timber
import java.util.concurrent.TimeUnit

@HiltAndroidApp
class ObjectivesApplication : Application() {

    private lateinit var workManager: WorkManager

    override fun onCreate() {
        super.onCreate()
        PreferenceHelper.getInstance(this)
        workManager = WorkManager.getInstance(this)
        context = this
        if (BuildConfig.DEBUG) {
            Timber.plant(Timber.DebugTree())
        }
        enqueueWorker()
    }


    companion object {
        @SuppressLint("StaticFieldLeak")
        var context: Context? = null
    }


    private fun enqueueWorker() {
        workManager.enqueue(getPostWorker())
        workManager.enqueue(getPhotoWorker())
    }

    private fun getWorkConstraints(): Constraints {

        return Constraints.Builder()
            .setRequiredNetworkType(NetworkType.CONNECTED)
            .build()
    }

    private fun getPostWorker(): PeriodicWorkRequest {

        return PeriodicWorkRequestBuilder<PostWorker>(15, TimeUnit.MINUTES)
            .addTag(TAG_POST)
            .setConstraints(getWorkConstraints())
            .setBackoffCriteria(
                BackoffPolicy.LINEAR,
                PeriodicWorkRequest.MIN_BACKOFF_MILLIS,
                TimeUnit.MILLISECONDS
            )
            .build()
    }

    private fun getPhotoWorker(): PeriodicWorkRequest {

        return PeriodicWorkRequestBuilder<PhotoWorker>(15, TimeUnit.MINUTES)
            .addTag(TAG_PHOTOS)
            .setConstraints(getWorkConstraints())
            .setBackoffCriteria(
                BackoffPolicy.LINEAR,
                PeriodicWorkRequest.MIN_BACKOFF_MILLIS,
                TimeUnit.MILLISECONDS
            )
            .build()
    }
}