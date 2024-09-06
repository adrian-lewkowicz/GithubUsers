package com.example.github.database

import androidx.room.Database
import androidx.room.RoomDatabase
import com.example.github.database.dao.UsersDao
import com.example.github.database.entities.GithubUserEntity

@Database(entities = [GithubUserEntity::class], version = 1)
abstract class AppDatabase :RoomDatabase() {
    abstract fun usersDao():UsersDao
}