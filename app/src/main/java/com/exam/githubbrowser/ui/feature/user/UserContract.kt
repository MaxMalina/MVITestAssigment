package com.exam.githubbrowser.ui.feature.user

import com.exam.githubbrowser.data.model.User
import com.exam.githubbrowser.ui.base.ViewEvent
import com.exam.githubbrowser.ui.base.ViewSideEffect
import com.exam.githubbrowser.ui.base.ViewState

class UserContract {

    sealed class Event : ViewEvent {
        object Retry : Event()
        data class UserSelection(val user: User) : Event()
    }

    data class State(
        val users: List<User>,
        val isLoading: Boolean,
        val isError: Boolean,
    ) : ViewState

    sealed class Effect : ViewSideEffect {
        object DataWasLoaded : Effect()

        sealed class Navigation : Effect() {
            data class ToRepos(val userId: String): Navigation()
        }
    }

}
