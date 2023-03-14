package com.exam.githubbrowser.ui.feature.user

import androidx.lifecycle.viewModelScope
import com.exam.githubbrowser.data.GithubRepository
import com.exam.githubbrowser.ui.base.BaseViewModel
import kotlinx.coroutines.launch

class UserViewModel(
    private val githubRepository: GithubRepository
) : BaseViewModel<UserContract.Event, UserContract.State, UserContract.Effect>() {

    init { getUsers() }

    override fun setInitialState() = UserContract.State(
        users = emptyList(),
        isLoading = true,
        isError = false,
    )

    override fun handleEvents(event: UserContract.Event) {
        when (event) {
            is UserContract.Event.UserSelection -> setEffect { UserContract.Effect.Navigation.ToRepos(event.user.userId) }
            is UserContract.Event.Retry -> getUsers()
        }
    }

    fun getUsers() {
        viewModelScope.launch {
            setState { copy(isLoading = true, isError = false) }

            githubRepository.getUsers()
                .onSuccess { users ->
                    setState { copy(users = users, isLoading = false) }
                    setEffect { UserContract.Effect.DataWasLoaded }
                }
                .onFailure {
                    setState { copy(isError = true, isLoading = false) }
                }
        }
    }
}
