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
	@field:SerializedName("name")
	val name: String,

	@field:SerializedName("bio")
	val bio: String?,

	avatarUrl: String,
	username: String
) : UserResponse(avatarUrl, username)

data class RepositoryResponse(
	@field:SerializedName("updated_at")
	val updatedAt: String,

	@field:SerializedName("stargazers_count")
	val stargazersCount: Int,

	@field:SerializedName("name")
	val name: String,

	@field:SerializedName("description")
	val description: String,
)