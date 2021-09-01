package com.example.githubsearch.api

import com.example.githubsearch.datamodel.UserList
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET
import retrofit2.http.Query

interface Api {
    @GET("search/users")
    suspend fun getSearchUsers(
        @Query("q") q: String,
        @Query("page") page: Int,
    ): Response<UserList>

    companion object {
        fun create() : Api {
            return Retrofit.Builder()
                .baseUrl("https://api.github.com/")
                .addConverterFactory(GsonConverterFactory.create())
                .build()
                .create(Api::class.java)
        }
    }
}