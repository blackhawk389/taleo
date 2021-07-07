package com.sarah.objectives.apiservice

import com.sarah.objectives.data.projects.Projects
import retrofit2.http.GET
import retrofit2.http.Path


interface PostAPIService {

    @GET("api/projects/all/{pageNo}")
    suspend fun getPaginatedProjects(@Path("pageNo") position: Int): Projects
}