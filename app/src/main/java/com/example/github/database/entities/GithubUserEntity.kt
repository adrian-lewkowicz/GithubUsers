package com.example.github.database.entities

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "users")
data class GithubUserEntity(
                            @PrimaryKey
                            @ColumnInfo("id") var id: Int? = null,
                            @ColumnInfo("login") var login: String? = null,
                            @ColumnInfo("avatar_url") var avatarUrl: String? = null,
                            @ColumnInfo("url") var url: String? = null,)
