package com.exam.githubbrowser.data.model

import com.google.gson.annotations.SerializedName

data class UserDetails(
    @SerializedName("avatar_url") val avatarUrl: String = "",
    @SerializedName("html_url") val htmlUrl: String = "",
    @SerializedName("name") val name: String = "",
    @SerializedName("location") val location: String? = "",
    @SerializedName("blog") val blogUrl: String = "",
    @SerializedName("public_repos") val publicRepos: Int = 0,
    @SerializedName("followers") val followers: Int = 0,
    @SerializedName("following") val following: Int = 0,
)

fun buildUserDetailsPreview() = UserDetails(
    avatarUrl = "https://avatars.githubusercontent.com/u/14333033?v=4",
    htmlUrl = "https://github.com/MaxMalina",
    name = "MaxMalina",
    location = "Kyiv, Ukraine",
    publicRepos = 123,
    followers = 123,
    following = 123,
)