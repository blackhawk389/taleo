package com.sarah.objectives.base

import com.google.gson.Gson
import com.sarah.objectives.config.network.NoConnectivityException
import com.sarah.objectives.utils.Constants
import retrofit2.Response
import timber.log.Timber
import java.io.BufferedReader
import java.io.IOException
import java.io.InputStreamReader


abstract class BaseResponse {

    private var gson: Gson = Gson()

    protected suspend fun <T> getResponse(call: suspend () -> Response<T>): Resource<T> {

        return try {
            val response = call()
            if (response.code() >= 400) {
                responseErrorToJson(response)
            } else {
                responseErrorToJson(response)
            }
        } catch (networkException: NoConnectivityException) {
            val response = BaseErrorResponse("", false, networkException.message)
            error(networkException.message, response)
        } catch (e: Exception) {
            val response = BaseErrorResponse("", false, e.message.toString())
            error(e.message ?: e.toString(), response)
        }

    }

    private fun <T> error(message: String, responseError: BaseErrorResponse): Resource<T> {
        Timber.d(message)
        return Resource.error(message, null, responseError)
    }

    /*
    * After mapping the string converted error to BaseErrorResponse return error as Resource<T>
    * */
    private fun <T> responseErrorToJson(response: Response<T>): Resource<T> {
        if (response.isSuccessful) {
            val body = response.body()
            if (body != null) return Resource.success(body)
        }
        val json = errorBodyToString(response)
        val responseError = gson.fromJson(json, BaseErrorResponse::class.java)
        if (responseError.error == Constants.NETWORK_CONSTANTS.unAuthorizeAccessMessage){

            return error("",responseError)
        }
        return error("", responseError)
    }

    /*
    * This will map the calling API error if any and return it as string to further map on Our BaseErrorResponse
    * */
    private fun <T> errorBodyToString(response: Response<T>): String {
        val reader: BufferedReader?
        val sb = StringBuilder()
        try {
            reader = BufferedReader(InputStreamReader(response.errorBody()?.byteStream()))
            var line: String?
            try {
                while (reader.readLine().also { line = it } != null) {
                    sb.append(line)
                }
            } catch (e: IOException) {
                e.printStackTrace()
            }
        } catch (e: IOException) {
            e.printStackTrace()
        }
        return sb.toString()
    }

}
