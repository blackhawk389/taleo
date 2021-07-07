package com.sarah.objectives.di

import android.content.Context
import com.google.gson.Gson
import com.google.gson.GsonBuilder
import com.sarah.objectives.apiservice.*
import com.sarah.objectives.app.ObjectivesApplication
import com.sarah.objectives.config.db.ObjectiveDatabase
import com.sarah.objectives.config.network.NetworkClient
import com.sarah.objectives.datasource.*
import com.sarah.objectives.repositories.*
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


    //User(works on register and login)

    @Provides
    fun getUserAPIService(retrofit: Retrofit): UserAPIService = retrofit.create(UserAPIService::class.java)

    @Provides
    fun getUserDataSource(apiService: UserAPIService) = UserRemoteDataSource(apiService)

    @Provides
    fun getUserRepository(dataSource: UserRemoteDataSource) = UserRepository(dataSource)

    //Home

    @Provides
    fun getHomeAPIService(retrofit: Retrofit): HomeAPIService = retrofit.create(HomeAPIService::class.java)

    @Provides
    fun getHomeDataSource(apiService: HomeAPIService) = HomeDataSource(apiService)

    @Provides
    fun getHomeRepository(dataSource: HomeDataSource) = HomeRepository(dataSource)

    //Blog

    @Provides
    fun getBlogApiService(retrofit: Retrofit): PhotoAPIService = retrofit.create(PhotoAPIService::class.java)

    @Provides
    fun getBlogDataSource(apiService: PhotoAPIService, db:ObjectiveDatabase) = BlogDataSource(apiService,db)


    @Provides
    fun getBlogPagingSource(apiService: PhotoAPIService) = PhotoDataSource(apiService)

    @Provides
    fun getBlogRepository(dataSource: BlogDataSource,pagingDataSource:PhotoDataSource) = PostRepository(dataSource,pagingDataSource)

    //Project

    @Provides
    fun getProjectApiService(retrofit: Retrofit): PostAPIService = retrofit.create(PostAPIService::class.java)

    @Provides
    fun getProjectDataSource(apiService: PostAPIService, db:ObjectiveDatabase) = PostDataSource(apiService,db)

    @Provides
    fun getProjectPagedSource(apiService: PostAPIService) = PhotoPagedDataSource(apiService)

    @Provides
    fun getProjectRepository(dataSource: PostDataSource, pagingDataDataSource:PhotoPagedDataSource) = PhotoRepository(dataSource,pagingDataDataSource)

}