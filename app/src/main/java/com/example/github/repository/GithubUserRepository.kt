package com.example.github.repository

import androidx.lifecycle.LiveData
import com.example.github.database.entities.GithubUserEntity

interface GithubUserRepository {
    val users: LiveData<List<GithubUserEntity>>
    suspend fun getUsers()
}