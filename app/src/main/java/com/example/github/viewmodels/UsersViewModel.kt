package com.example.github.viewmodels

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.github.database.entities.GithubUserEntity
import com.example.github.repository.GithubUserRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class UsersViewModel @Inject constructor(private val repository: GithubUserRepository):ViewModel(){

    init {
        viewModelScope.launch {
            repository.getUsers()
        }

    }

    fun getUsers():LiveData<List<GithubUserEntity>>{

        return repository.users
    }
}