package com.fajarsn.githubusersapp.di

import android.content.Context
import com.fajarsn.githubusersapp.data.remote.ApiConfig
import com.fajarsn.githubusersapp.data.repository.UserRepository

object Injection {
    fun provideUser(context: Context) : UserRepository {
        val service = ApiConfig.getApiService(context)
        return UserRepository.getInstance(service)
    }
}