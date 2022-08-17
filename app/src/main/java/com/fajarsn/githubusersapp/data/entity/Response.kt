package com.fajarsn.githubusersapp.data.entity

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.parcelize.Parcelize

@Parcelize
open class UserResponse(

	@field:SerializedName("avatar_url")
	val avatarUrl: String,

	@field:SerializedName("login")
	val username: String
) : Parcelable

data class UserSearchResponse(
	@field:SerializedName("items")
	val items: List<UserResponse>
)

class UserDetailResponse(

	@field:SerializedName("repos_url")
	val reposUrl: String,

	@field:SerializedName("name")
	val name: String,

	avatarUrl: String,
	username: String
) : UserResponse(avatarUrl, username)