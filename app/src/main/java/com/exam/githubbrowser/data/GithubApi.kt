package com.exam.githubbrowser.data

import com.exam.githubbrowser.data.model.Repo
import com.exam.githubbrowser.data.model.User
import com.exam.githubbrowser.data.model.UserDetails
import retrofit2.http.GET
import retrofit2.http.Path

interface GithubApi {
    @GET(Urls.USERS)
    suspend fun getUsers(): List<User>

    @GET(Urls.USER)
    suspend fun getUser(@Path("userLogin") userId: String): UserDetails?

    @GET(Urls.REPOS_BY_USER)
    suspend fun getRepos(@Path("userLogin") userId: String): List<Repo>
}