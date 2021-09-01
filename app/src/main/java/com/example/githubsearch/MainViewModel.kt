package com.example.githubsearch

import android.app.Application
import android.util.Log
import androidx.lifecycle.MutableLiveData
import com.example.githubsearch.api.Api
import com.example.githubsearch.base.BaseLoadingViewModel
import com.example.githubsearch.datamodel.Result
import com.example.githubsearch.datamodel.User
import com.example.githubsearch.utils.getResult
import com.example.githubsearch.utils.safeApiCall

class MainViewModel(application: Application) : BaseLoadingViewModel(application) {

    private val api get() = Api.create()
    private var currentPage = 1
    val useListLiveData = MutableLiveData<List<User>>()

    fun searchUsers(key: String) {
        sendApi {
            getSearchUsers(key, currentPage).handleResult {
                useListLiveData.value = it.userList
            }
        }
    }

    fun loadNextPage() {
        // TODO
    }

    private suspend fun getSearchUsers(key: String, page: Int) = safeApiCall {
        api.getSearchUsers(key, 1).getResult()
    }
}