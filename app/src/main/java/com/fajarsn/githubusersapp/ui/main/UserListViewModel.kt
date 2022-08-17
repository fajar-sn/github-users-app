package com.fajarsn.githubusersapp.ui.main

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.fajarsn.githubusersapp.data.repository.Result
import com.fajarsn.githubusersapp.data.repository.UserRepository
import kotlinx.coroutines.launch

open class BaseViewModel(protected open val repository: UserRepository) : ViewModel() {
    protected val mutableResult = MutableLiveData<Result>()
    val result : LiveData<Result> = mutableResult
}

class UserListViewModel(repository: UserRepository) : BaseViewModel(repository) {
    init {
        getUserList()
    }

    fun getUserList() = viewModelScope.launch {
        mutableResult.value = Result.Loading
        repository.getUserList(mutableResult)
    }

    fun searchUser(username: String) = viewModelScope.launch {
        mutableResult.value = Result.Loading
        repository.searchUser(username, mutableResult)
    }
}