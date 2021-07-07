package com.sarah.objectives.config.network

import com.sarah.objectives.events.LogoutEvent
import com.sarah.objectives.preferences.PreferenceHelper
import okhttp3.Interceptor
import okhttp3.Response
import org.greenrobot.eventbus.EventBus


class AuthInterceptor : Interceptor {

    override fun intercept(chain: Interceptor.Chain): Response {
        val request = chain.request()
        val token = PreferenceHelper.getToken()
        var authToken =""
        if (token.isNullOrBlank()){
            EventBus.getDefault().post(LogoutEvent())
        } else {
            authToken = "Bearer $token"
        }

        val authRequest = request.newBuilder().addHeader("Authorization",authToken).build()
        return chain.proceed(authRequest)
    }

}