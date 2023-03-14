package com.exam.githubbrowser.data

import com.exam.githubbrowser.data.model.Repo
import com.exam.githubbrowser.data.model.User
import com.exam.githubbrowser.data.model.UserDetails
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.confirmVerified
import io.mockk.mockk
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runTest
import org.junit.Test

class GithubRepositoryTest {

    private val githubApi = mockk<GithubApi>()
    private val githubRepository: GithubRepository = GithubRepositoryImpl(githubApi)

    @Test
    fun `when getUsers called then call getUsers from API`() = runTest {
        val users = listOf(User())
        coEvery { githubApi.getUsers() } returns users

        val result = githubRepository.getUsers()

        assert(result.isSuccess)
        coVerify(exactly = 1) { githubApi.getUsers() }
        confirmVerified(githubApi)
    }

    @Test
    fun `when getUser called then call getUser from API`() = runTest {
        val userId = "wcabral"
        coEvery { githubApi.getUser(any()) } returns UserDetails()

        val result = githubRepository.getUser(userId)

        assert(result.isSuccess)
        coVerify(exactly = 1) { githubApi.getUser(userId) }
        confirmVerified(githubApi)
    }

    @Test
    fun `when getRepos called then call getRepos from API`() = runTest {
        val userId = "wcabral"
        coEvery { githubApi.getRepos(userId) } returns listOf(Repo())

        val result = githubRepository.getRepos(userId)

        assert(result.isSuccess)
        coVerify(exactly = 1) { githubApi.getRepos(userId) }
        confirmVerified(githubApi)
    }

    @Test
    fun `when getUsers called then returns failure`() = runTest {
        coEvery { githubApi.getUsers() } throws Exception("")

        val result = githubRepository.getUsers()

        assert(result.isFailure)
    }

    @Test
    fun `when getUser called then returns failure`() = runTest {
        coEvery { githubApi.getUser(any()) } throws Exception("")

        val result = githubRepository.getUser("wcabral")

        assert(result.isFailure)
    }

    @Test
    fun `when getRepos called then returns failure`() = runTest {
        coEvery { githubApi.getRepos(any()) } throws Exception("")

        val result = githubRepository.getRepos("wcabral")

        assert(result.isFailure)
    }
}