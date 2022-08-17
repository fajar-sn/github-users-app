package com.fajarsn.githubusersapp.data.repository

import androidx.lifecycle.MutableLiveData
import com.fajarsn.githubusersapp.data.remote.ApiService

class UserRepository private constructor(private val service: ApiService) : BaseRepository() {
    suspend fun getUserList(token: String, liveData: MutableLiveData<Result>) = try {
        val response = service.getUserList("token $token")
        liveData.value = Result.Success(response)
    } catch (exception: Exception) {
        catchError(exception, liveData)
    }
}