package com.example.githubsearch

import android.app.Application
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.example.githubsearch.base.BaseLoadingViewModel
import com.example.githubsearch.base.SingleLiveEvent
import com.example.githubsearch.datamodel.User
import kotlinx.coroutines.launch

class MainViewModel(application: Application) : BaseLoadingViewModel(application) {

    private val remoteDataSource = MainRemoteDataSource()
    private var currentPage = 1
    var searchKey = ""
    val needToLoadNextEvent = SingleLiveEvent<Void>()
    val useListLiveData = MutableLiveData<List<SearchUiModel>>()

    fun searchUsers() {
        useListLiveData.value = listOf()
        currentPage = 1

        sendApi {
            getSearchUsers().handleResult { data ->
                useListLiveData.value = mutableListOf<SearchUiModel>().apply {
                    addAll(data.users.map { user -> SearchUiModel.UserUiModel(user) })
                    if (size < data.totalCount) add(SearchUiModel.LoadingUiModel)
                }
            }
        }
    }

    fun loadNextPage() {
        currentPage++

        viewModelScope.launch {
            getSearchUsers().handleResult { data ->
                useListLiveData.value = mutableListOf<SearchUiModel>().apply {
                    useListLiveData.value?.also { addAll(it.subList(0, it.size-1)) }
                    addAll(data.users.map { user -> SearchUiModel.UserUiModel(user) })
                    if (size < data.totalCount) add(SearchUiModel.LoadingUiModel)
                }
            }
        }
    }

    private suspend fun getSearchUsers() = remoteDataSource.getSearchUsers(searchKey, currentPage)
}

sealed class SearchUiModel {
    class UserUiModel(val user: User) : SearchUiModel()
    object LoadingUiModel : SearchUiModel()
}