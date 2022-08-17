package com.fajarsn.githubusersapp.data.remote

import com.fajarsn.githubusersapp.data.entity.UserResponse
import retrofit2.http.GET

interface ApiService {
    @GET("users")
    suspend fun getUserList() : List<UserResponse>
}