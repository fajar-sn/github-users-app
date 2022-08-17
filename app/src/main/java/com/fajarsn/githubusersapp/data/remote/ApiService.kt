package com.fajarsn.githubusersapp.data.remote

import com.fajarsn.githubusersapp.data.entity.UserListResponse
import retrofit2.http.GET
import retrofit2.http.Header

interface ApiService {
    @GET("users")
    suspend fun getUserList(@Header("Authorization") authorization: String) : UserListResponse
}