package com.fajarsn.githubusersapp.ui.main

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.fajarsn.githubusersapp.data.repository.BaseRepository
import com.fajarsn.githubusersapp.data.repository.Result
import com.fajarsn.githubusersapp.data.repository.UserRepository
import kotlinx.coroutines.launch

open class BaseViewModel(protected open val repository: BaseRepository) : ViewModel() {
    protected val mutableResult = MutableLiveData<Result>()
    val result : LiveData<Result> = mutableResult
}

class UserListViewModel(override val repository: UserRepository) : BaseViewModel(repository) {
    fun getUserList(token: String) = viewModelScope.launch {
        mutableResult.value = Result.Loading
        repository.getUserList(token, mutableResult)
    }
}