package com.fajarsn.githubusersapp.data.remote

import android.content.Context
import android.content.pm.PackageManager
import android.util.Log
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import com.fajarsn.githubusersapp.BuildConfig

object ApiConfig {
    private const val baseUrl = "https://api.github.com/"
    private lateinit var token: String

    fun getApiService(context: Context): ApiService {
        val loggingInterceptor =
            if (BuildConfig.DEBUG)
                HttpLoggingInterceptor().setLevel(HttpLoggingInterceptor.Level.BODY)
            else
                HttpLoggingInterceptor().setLevel(HttpLoggingInterceptor.Level.NONE)

        context.packageManager
            .getApplicationInfo(context.packageName, PackageManager.GET_META_DATA)
            .apply {
                token = metaData.getString("TOKEN").toString()
            }

        val client = OkHttpClient.Builder()
            .addInterceptor(loggingInterceptor)

            // TODO: Uncomment those lines if limit reached
//            .addInterceptor { chain ->
//                val builder = chain.request().newBuilder()
//                builder.header("Authorization", "token $token")
//                return@addInterceptor chain.proceed(builder.build())
//            }
            .build()

        val retrofit = Retrofit.Builder()
            .baseUrl(baseUrl)
            .addConverterFactory(GsonConverterFactory.create())
            .client(client).build()

        return retrofit.create(ApiService::class.java)
    }
}