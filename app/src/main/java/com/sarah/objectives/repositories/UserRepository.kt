package com.sarah.objectives.repositories

import com.ovais.objectives.data.user.UserResponse
import com.sarah.objectives.base.BaseRepository
import com.sarah.objectives.base.Resource
import com.sarah.objectives.data.common.GeneralResponse
import com.sarah.objectives.data.token.TokenResponse
import com.sarah.objectives.data.user.User
import com.sarah.objectives.datasource.UserRemoteDataSource
import com.sarah.objectives.requestbody.TokenRequestBody
import javax.inject.Inject

class UserRepository @Inject constructor(private var dataSource: UserRemoteDataSource): BaseRepository() {

    suspend fun registerUser(user:User): Resource<GeneralResponse> {
        return dataSource.registerUser(user)
    }

    suspend fun enqueueTokenRequest(email:String,password:String): Resource<TokenResponse> {
        val makeTokenRequestBody = TokenRequestBody(email,password)
        return dataSource.enqueueTokenRequest(makeTokenRequestBody)
    }

    suspend fun getUserInfo():Resource<UserResponse> = dataSource.getUserInfo()
}