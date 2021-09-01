package com.example.githubsearch.utils

import com.example.githubsearch.datamodel.NetworkErrorException
import com.example.githubsearch.datamodel.Result
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

suspend fun <T : Any> safeApiCall(call: suspend () -> Result<T>): Result<T> {
    return try {
        withContext(Dispatchers.IO) {
            call()
        }
    } catch (e: Exception) {
        e.printStackTrace()
        // An exception was thrown when calling the API so we're converting this to NetworkErrorException
        Result.Error(NetworkErrorException)
    }
}