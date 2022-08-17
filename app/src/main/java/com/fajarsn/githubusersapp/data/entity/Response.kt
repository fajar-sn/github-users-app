package com.fajarsn.githubusersapp.data.entity

import com.google.gson.annotations.SerializedName

open class UserResponse(

	@field:SerializedName("avatar_url")
	val avatarUrl: String,

	@field:SerializedName("login")
	val username: String
)

class UserDetailResponse(

	@field:SerializedName("repos_url")
	val reposUrl: String,

	@field:SerializedName("name")
	val name: String,

	avatarUrl: String,
	username: String
) : UserResponse(avatarUrl, username)