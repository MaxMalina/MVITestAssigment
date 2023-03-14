package com.exam.githubbrowser.data.model

import com.google.gson.annotations.SerializedName

data class User(
    @SerializedName("login") val userId: String = "",
    @SerializedName("avatar_url") val avatarUrl: String = "",
    @SerializedName("html_url") val htmlUrl: String = "",
)

fun buildUserPreview() = User(
    userId = "MaxMalina",
    avatarUrl = "https://avatars.githubusercontent.com/u/14333033?v=4",
    htmlUrl = "https://github.com/MaxMalina",
)