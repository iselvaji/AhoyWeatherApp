package com.ahoy.weatherapp.data.remote

import android.util.Log
import com.ahoy.weatherapp.comman.Resource
import retrofit2.Response

abstract class BaseDataSource {

    suspend fun <T> getResult(apiCall: suspend () -> Response<T>): Resource<T> {
        try {
            val response = apiCall()
            if (response.isSuccessful) {
                val body = response.body()
                Log.i("response body", body.toString())
                body?.let {
                    return Resource.success(body)
                }
            }
            return error("${response.code()} ${response.message()}")
        } catch (e: Exception) {
            Log.d("response exception : ", e.printStackTrace().toString())
            return error(e.message ?: e.toString())
        }
    }

    private fun <T> error(errorMessage: String): Resource<T> =
        Resource.error("Api call failed $errorMessage")
}