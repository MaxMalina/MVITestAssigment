package com.exam.githubbrowser.ui

import com.exam.githubbrowser.data.GithubRepository
import com.exam.githubbrowser.data.model.User
import com.exam.githubbrowser.ui.feature.user.UserContract
import com.exam.githubbrowser.ui.feature.user.UserViewModel
import io.mockk.coEvery
import io.mockk.mockk
import kotlinx.coroutines.DelicateCoroutinesApi
import kotlinx.coroutines.test.runTest
import org.junit.Rule
import org.junit.Test
import kotlin.test.assertEquals

@DelicateCoroutinesApi
class UsersViewModelTest {

    @get:Rule
    val mainCoroutineRule = MainCoroutineRule()

    private val githubRepository = mockk<GithubRepository>()

    @Test
    fun `when view model initialized then emit initial view state`() = runTest {
        val expectedInitialViewState = UserContract.State(
            users = emptyList(),
            isLoading = true,
            isError = false
        )

        val viewModel = UserViewModel(githubRepository)

        assertEquals(expectedInitialViewState, viewModel.viewState.value)
    }

    @Test
    fun `when getUsers called then emit a view state`() = runTest {
        val users = listOf(User(userId = "wcabral"))
        val expectedViewState = UserContract.State(
            users = users,
            isLoading = false,
            isError = false
        )
        coEvery { githubRepository.getUsers() } returns Result.success(users)

        val viewModel = UserViewModel(githubRepository)

        assertEquals(expectedViewState, viewModel.viewState.value)
    }
}