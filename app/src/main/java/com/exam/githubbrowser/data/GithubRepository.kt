package com.exam.githubbrowser.data

import com.exam.githubbrowser.data.model.Repo
import com.exam.githubbrowser.data.model.User
import com.exam.githubbrowser.data.model.UserDetails

interface GithubRepository {
    suspend fun getUsers(): Result<List<User>>
    suspend fun getUser(userId: String): Result<UserDetails?>
    suspend fun getRepos(userId: String): Result<List<Repo>>
}