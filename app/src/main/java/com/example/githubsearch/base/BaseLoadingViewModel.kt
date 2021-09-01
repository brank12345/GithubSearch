package com.example.githubsearch.base

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import com.example.githubsearch.datamodel.NetworkErrorException
import com.example.githubsearch.datamodel.Result
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch

abstract class BaseLoadingViewModel(application: Application) : AndroidViewModel(application) {
    val errorMsgEvent = SingleLiveEvent<String>()
    val noInternetEvent = SingleLiveEvent<Void>()

    val isLoading = SingleLiveEvent<Boolean>()

    protected fun sendApi(apiAction: suspend CoroutineScope.() -> Unit) {
        viewModelScope.launch {
            isLoading.postValue(true)
            apiAction()
            isLoading.postValue(false)
        }
    }

    protected fun <T> Result<T>.handleResult(successAction: (T) -> Unit = {}) {
        when (this) {
            is Result.Success -> {
                successAction(data)
            }
            is Result.Error -> {
                handleError()
            }
        }
    }

    protected fun List<Result<Any>>.handleResults(successAction: () -> Unit) {
        when {
            all { it is Result.Success } -> successAction()
            else -> (find { it is Result.Error } as? Result.Error)?.handleError()
        }
    }

    protected open fun Result.Error.handleError() {
        when(exception) {
            is NetworkErrorException -> noInternetEvent.call()
            else -> errorMsgEvent.postValue(exception.message)
        }
    }
}