package com.example.github.database.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.github.database.entities.GithubUserEntity

@Dao
interface UsersDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAll(usersList: List<GithubUserEntity>)

    @Query("SELECT * FROM users")
    suspend fun getAll() : List<GithubUserEntity>
}