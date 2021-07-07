package com.sarah.objectives.datasource

import com.sarah.objectives.apiservice.UserAPIService
import com.sarah.objectives.base.BaseResponse
import com.sarah.objectives.data.user.User
import com.sarah.objectives.requestbody.TokenRequestBody
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.MultipartBody
import okhttp3.RequestBody
import okhttp3.RequestBody.Companion.toRequestBody
import java.io.File
import javax.inject.Inject


class UserRemoteDataSource @Inject constructor(private var apiService: UserAPIService) :
    BaseResponse() {

    suspend fun registerUser(user: User) = getResponse {

        val requestFile = RequestBody.create("image/png".toMediaTypeOrNull(), File(user.profile!!.path))
        // MultipartBody.Part is used to send also the actual file name
        val body: MultipartBody.Part =
            MultipartBody.Part.createFormData("profileImage", user.profile!!.name, requestFile)

        apiService.registerUser(
            user.fullName!!.toRequestBody("text/plain".toMediaTypeOrNull()),
            user.email!!.toRequestBody("text/plain".toMediaTypeOrNull()),
            user.phone!!.toRequestBody("text/plain".toMediaTypeOrNull()),
            user.password!!.toRequestBody("text/plain".toMediaTypeOrNull()),
            body,
            user.address!!.toRequestBody("text/plain".toMediaTypeOrNull()),
            user.dateOfBirth!!.toRequestBody("text/plain".toMediaTypeOrNull())
        )
    }

    suspend fun enqueueTokenRequest(requestBody: TokenRequestBody) = getResponse {

        apiService.enqueueTokenRequest(requestBody.email,requestBody.password)
    }

    suspend fun getUserInfo() = getResponse {
        apiService.getUserInfo()
    }

}