package com.fajarsn.githubusersapp.ui.helper

import android.content.Context
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.fajarsn.githubusersapp.data.repository.UserRepository
import com.fajarsn.githubusersapp.di.Injection
import com.fajarsn.githubusersapp.ui.detail.UserDetailViewModel
import com.fajarsn.githubusersapp.ui.main.UserListViewModel

@Suppress("UNCHECKED_CAST")
class ViewModelFactory private constructor(
    private val repository: UserRepository,
) : ViewModelProvider.NewInstanceFactory() {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(UserListViewModel::class.java))
            return UserListViewModel(repository) as T
        if (modelClass.isAssignableFrom(UserDetailViewModel::class.java))
            return UserDetailViewModel(repository) as T

        throw IllegalArgumentException("Unknown ViewModel class: ${modelClass.name}")
    }

    companion object {
        @Volatile
        private var instance: ViewModelFactory? = null

        fun getInstance(context: Context) =
            instance ?: synchronized(this) {
                instance ?: ViewModelFactory(Injection.provideUser(context))
            }
    }
}