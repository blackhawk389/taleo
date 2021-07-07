package com.sarah.objectives.apiservice

import com.ovais.objectives.data.user.UserResponse
import com.sarah.objectives.data.common.GeneralResponse
import com.sarah.objectives.data.token.TokenResponse
import okhttp3.MultipartBody
import okhttp3.RequestBody
import retrofit2.Response
import retrofit2.http.*

interface UserAPIService {

    @Multipart
    @POST("api/public/user/signup")
    suspend fun registerUser(
        @Part("fullname") fullName: RequestBody,
        @Part("email") email: RequestBody,
        @Part("phone") phone: RequestBody,
        @Part("password") password: RequestBody,
        @Part profileImage: MultipartBody.Part?,
        @Part("address") address: RequestBody,
        @Part("dob") dob: RequestBody
    ): Response<GeneralResponse>


    @FormUrlEncoded
    @POST("api/public/user/")
    suspend fun enqueueTokenRequest(
        @Field("email") email: String,
        @Field("password") password: String
    ): Response<TokenResponse>

    @GET("api/user/")
    suspend fun getUserInfo() : Response<UserResponse>
}