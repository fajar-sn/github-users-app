package com.fajarsn.githubusersapp.data.remote

import com.fajarsn.githubusersapp.data.entity.UserResponse
import com.fajarsn.githubusersapp.data.entity.UserSearchResponse
import retrofit2.http.GET
import retrofit2.http.Query

interface ApiService {
    @GET("users")
    suspend fun getUserList() : List<UserResponse>

    @GET("search/users")
    suspend fun searchUser(@Query("q") username: String) : UserSearchResponse
}