package com.example.githubsearch.utils

import retrofit2.Response
import com.example.githubsearch.datamodel.Result
import com.example.githubsearch.datamodel.ServerErrorException

fun <T : Any> Response<T>.getResult(): Result<T> {
    return if (isSuccessful) {
        val body = body()
        if (body != null) {
            Result.Success(body)
        } else {
            Result.Error(ServerErrorException(code().toString(), "Unexpected response body: null"))
        }
    } else {
        Result.Error(ServerErrorException(code().toString(), "Response is not successful"))
    }
}