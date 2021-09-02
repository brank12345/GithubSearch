package com.example.githubsearch.datamodel

import com.google.gson.annotations.SerializedName

data class UserList(
    @SerializedName("total_count") val totalCount: Int,
    @SerializedName("items") val users: List<User>
)

data class User(
    @SerializedName("login") val name: String,
    @SerializedName("avatar_url") val avatarUrl: String
)