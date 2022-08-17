package com.fajarsn.githubusersapp.data.repository

import android.util.Log
import androidx.lifecycle.MutableLiveData
import com.fajarsn.githubusersapp.BuildConfig
import com.fajarsn.githubusersapp.data.remote.ApiService
import java.net.ConnectException
import java.net.UnknownHostException

sealed class Result private constructor() {
    data class Success<out T>(val data: T) : Result()
    data class Error(val error: String) : Result()
    object Loading : Result()
}

class UserRepository private constructor(private val service: ApiService) {
    suspend fun getUserList(liveData: MutableLiveData<Result>) = try {
        val response = service.getUserList()
        liveData.value = Result.Success(response)
    } catch (exception: Exception) {
        catchError(exception, liveData)
    }

    suspend fun searchUser(username: String, liveData: MutableLiveData<Result>) = try {
        val response = service.searchUser(username)
        liveData.value = Result.Success(response)
    } catch (exception: Exception) {
        catchError(exception, liveData)
    }

    private fun catchError(exception: Exception, liveData: MutableLiveData<Result>) =
        when (exception) {
            is UnknownHostException -> liveData.value =
                Result.Error("Please check your internet connection and try again.")
            is ConnectException -> liveData.value =
                Result.Error("Please check your internet connection and try again.")
            else -> liveData.value = exception.message?.let {
                if (BuildConfig.DEBUG) {
                    Log.e("TAGG", "${exception.message}\n")
                    exception.printStackTrace()
                }
                Result.Error(it)
            } as Result.Error
        }

    companion object {
        @Volatile
        private var instance: UserRepository? = null

        fun getInstance(service: ApiService) =
            instance ?: synchronized(this) { instance ?: UserRepository(service) }.also {
                instance = it
            }
    }
}