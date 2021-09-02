package com.example.githubsearch

import com.example.githubsearch.api.Api
import com.example.githubsearch.utils.getResult
import com.example.githubsearch.utils.safeApiCall

class MainRemoteDataSource {
    private val api get() = Api.create()

    suspend fun getSearchUsers(searchKey: String, currentPage: Int) = safeApiCall {
        api.getSearchUsers(searchKey, currentPage).getResult()
    }
}