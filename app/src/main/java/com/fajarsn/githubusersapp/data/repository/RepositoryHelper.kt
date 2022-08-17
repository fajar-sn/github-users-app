package com.fajarsn.githubusersapp.data.repository

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.viewbinding.BuildConfig
import java.net.ConnectException
import java.net.UnknownHostException

sealed class Result private constructor() {
    data class Success<out T>(val data: T) : Result()
    data class Error(val error: String) : Result()
    object Loading : Result()
}

open class BaseRepository {
    protected fun catchError(exception: Exception, liveData: MutableLiveData<Result>) =
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
}