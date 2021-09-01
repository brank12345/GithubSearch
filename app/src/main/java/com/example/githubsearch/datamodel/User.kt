package com.example.githubsearch.datamodel

import com.google.gson.annotations.SerializedName

data class UserList(
    @SerializedName("items") val userList: List<User>
)

data class User(
    @SerializedName("login") val name: String,
    @SerializedName("avatar_url") val avatarUrl: String
)