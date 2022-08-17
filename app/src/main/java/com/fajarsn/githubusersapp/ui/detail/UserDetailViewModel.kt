package com.fajarsn.githubusersapp.ui.detail

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.fajarsn.githubusersapp.data.repository.Result
import com.fajarsn.githubusersapp.data.repository.UserRepository
import kotlinx.coroutines.launch

class UserDetailViewModel(private val repository: UserRepository) : ViewModel() {
    private val mutableUserDetailResult = MutableLiveData<Result>()
    val userDetailResult : LiveData<Result> = mutableUserDetailResult

    private val mutableUserRepoListResult = MutableLiveData<Result>()
    val repoListResult : LiveData<Result> = mutableUserRepoListResult

    fun getUserDetail(username: String) = viewModelScope.launch {
        mutableUserDetailResult.value = Result.Loading
        repository.getUserDetail(username, mutableUserDetailResult)
    }

    fun getUserRepoList(username: String) = viewModelScope.launch {
        mutableUserRepoListResult.value = Result.Loading
        repository.getUserRepoList(username, mutableUserRepoListResult)
    }
}
