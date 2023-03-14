package com.exam.githubbrowser.ui.feature.repo

import com.exam.githubbrowser.data.model.Repo
import com.exam.githubbrowser.data.model.UserDetails
import com.exam.githubbrowser.ui.base.ViewEvent
import com.exam.githubbrowser.ui.base.ViewSideEffect
import com.exam.githubbrowser.ui.base.ViewState

class ReposContract {

    sealed class Event : ViewEvent {
        object Retry : Event()
        object BackButtonClicked : Event()
    }

    data class State(
        val user: UserDetails?,
        val reposList: List<Repo>,
        val isUserLoading: Boolean,
        val isReposLoading: Boolean,
        val isError: Boolean,
    ) : ViewState

    sealed class Effect : ViewSideEffect {
        sealed class Navigation : Effect() {
            object Back : Navigation()
        }
    }

}
