package com.example.githubsearch.utils

import retrofit2.Response
import com.example.githubsearch.datamodel.Result
import com.example.githubsearch.datamodel.ServerErrorException
import com.google.gson.Gson
import com.google.gson.annotations.SerializedName

fun <T : Any> Response<T>.getResult(): Result<T> {
    return if (isSuccessful) {
        val body = body()
        if (body != null) {
            Result.Success(body)
        } else {
            Result.Error(ServerErrorException(code().toString(), "Unexpected response body: null"))
        }
    } else {
        val error = Gson().fromJson(errorBody()?.string(), ErrorResponse::class.java)
        Result.Error(ServerErrorException(code().toString(), error?.message))
    }
}

data class ErrorResponse(
    @SerializedName("message") val message: String
)