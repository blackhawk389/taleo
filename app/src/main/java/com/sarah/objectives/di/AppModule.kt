package com.sarah.objectives.di

import android.content.Context
import com.google.gson.Gson
import com.google.gson.GsonBuilder
import com.sarah.objectives.apiservice.GeneralClient
import com.sarah.objectives.apiservice.HomeAPIService
import com.sarah.objectives.apiservice.PhotoAPIService
import com.sarah.objectives.apiservice.PostAPIService
import com.sarah.objectives.app.ObjectivesApplication
import com.sarah.objectives.config.db.ObjectiveDatabase
import com.sarah.objectives.config.network.NetworkClient
import com.sarah.objectives.datasource.HomeDataSource
import com.sarah.objectives.datasource.PhotoDataSource
import com.sarah.objectives.datasource.PostDataSource
import com.sarah.objectives.datasource.SplashDataSource
import com.sarah.objectives.repositories.HomeRepository
import com.sarah.objectives.repositories.PhotoRepository
import com.sarah.objectives.repositories.PostRepository
import com.sarah.objectives.repositories.SplashRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ApplicationComponent
import retrofit2.Retrofit
import javax.inject.Singleton


@Module
@InstallIn(ApplicationComponent::class)
object AppModule {

    @Singleton
    @Provides
    fun providesRetrofit(gson: Gson) = NetworkClient.getInstance(gson)

    @Provides
    fun providesGson(): Gson = GsonBuilder().create()

    @Provides
    fun getContext(): Context = ObjectivesApplication.context!!

    @Provides
    fun provideDb(context: Context) = ObjectiveDatabase(context)

    @Provides
    fun getGeneralAPIService(retrofit: Retrofit): GeneralClient = retrofit.create(GeneralClient::class.java)

    @Provides
    fun getSplashDataSource(apiService: GeneralClient, database: ObjectiveDatabase) = SplashDataSource(apiService, database)

    @Provides
    fun getSplashRepository(dataSource: SplashDataSource) = SplashRepository(dataSource)


    //Home

    @Provides
    fun getHomeAPIService(retrofit: Retrofit): HomeAPIService = retrofit.create(HomeAPIService::class.java)

    @Provides
    fun getHomeDataSource(apiService: HomeAPIService) = HomeDataSource(apiService)

    @Provides
    fun getHomeRepository(dataSource: HomeDataSource) = HomeRepository(dataSource)

    //Photos

    @Provides
    fun getPhotoApiService(retrofit: Retrofit): PhotoAPIService = retrofit.create(PhotoAPIService::class.java)

    @Provides
    fun getPhotoDataSource(apiService: PhotoAPIService, db:ObjectiveDatabase) = PhotoDataSource(apiService,db)


    @Provides
    fun getPhotoRepository(dataSource: PhotoDataSource) = PhotoRepository(dataSource)

    //Posts

    @Provides
    fun getPostApiService(retrofit: Retrofit): PostAPIService = retrofit.create(PostAPIService::class.java)

    @Provides
    fun getPostDataSource(apiService: PostAPIService, db:ObjectiveDatabase) = PostDataSource(apiService,db)

    @Provides
    fun getPostRepository(dataSource: PostDataSource) = PostRepository(dataSource)

}