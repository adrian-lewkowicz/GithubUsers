package com.example.github.remote.data

import com.google.gson.annotations.SerializedName

data class GithubUser(
    @SerializedName("login") var login: String? = null,
    @SerializedName("id") var id: Int? = null,
    @SerializedName("avatar_url") var avatarUrl: String? = null,
    @SerializedName("url") var url: String? = null,
)
