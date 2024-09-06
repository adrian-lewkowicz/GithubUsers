package com.example.github.repository

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.github.database.dao.UsersDao
import com.example.github.database.entities.GithubUserEntity
import com.example.github.remote.data.GithubApi
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import javax.inject.Inject

class GithubUserRepositoryImpl @Inject constructor(
    private val usersDao: UsersDao,
    private val apiService: GithubApi,
    private val dispatcherIO :CoroutineDispatcher = Dispatchers.IO
): GithubUserRepository {
    private val TAG = GithubUserRepositoryImpl::class.simpleName

    private val _users = MutableLiveData<List<GithubUserEntity>>()
    override val users: LiveData<List<GithubUserEntity>> get() = _users

    override suspend fun getUsers() {
        val dbUsers = usersDao.getAll()
        _users.postValue(dbUsers)

        refreshUsers()
    }

    private suspend fun refreshUsers() {
        withContext(dispatcherIO) {
            try {
                val response = apiService.getUsers(0).execute()

                if (response.isSuccessful) {
                    response.body()?.let { usersList ->
                        val usersEntities = usersList.map { user ->
                            GithubUserEntity(
                                id = user.id,
                                login = user.login,
                                avatarUrl = user.avatarUrl,
                                url = user.url
                            )
                        }

                        usersDao.insertAll(usersEntities)

                        val updatedUsers = usersDao.getAll()
                        _users.postValue(updatedUsers)
                    }
                }else {
                    Log.e(TAG, "Response not successful: ${response.code()}")
                }
            } catch (e: Exception) {
                Log.e(TAG, e.message.toString())
            }
        }
    }
}