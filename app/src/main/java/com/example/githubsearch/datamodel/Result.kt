package com.example.githubsearch.datamodel

sealed class Result<out R> {

    data class Success<out T>(val data: T) : Result<T>()
    data class Error(val exception: Exception) : Result<Nothing>()


    override fun toString(): String {
        return when (this) {
            is Success<*> -> "Success[data=$data]"
            is Error -> "Error[exception=$exception]"
        }
    }
}

class ServerErrorException(val errorCode: String?, message: String?): Exception(message) {
    override fun toString() = "ServerErrorException(errorCode=$errorCode, message=$message)"
}
object NetworkErrorException: Exception()