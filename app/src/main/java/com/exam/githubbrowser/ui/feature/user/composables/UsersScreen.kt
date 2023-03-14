package com.exam.githubbrowser.ui.feature.user.composables

import androidx.compose.material.Scaffold
import androidx.compose.material.ScaffoldState
import androidx.compose.material.SnackbarDuration
import androidx.compose.material.rememberScaffoldState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import com.exam.githubbrowser.R
import com.exam.githubbrowser.data.model.buildUserPreview
import com.exam.githubbrowser.ui.base.SIDE_EFFECTS_KEY
import com.exam.githubbrowser.ui.feature.common.NetworkError
import com.exam.githubbrowser.ui.feature.common.Progress
import com.exam.githubbrowser.ui.feature.user.UserContract
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.onEach

@Composable
fun UsersScreen(
    state: UserContract.State,
    effectFlow: Flow<UserContract.Effect>?,
    onEventSent: (event: UserContract.Event) -> Unit,
    onNavigationRequested: (navigationEffect: UserContract.Effect.Navigation) -> Unit
) {
    val scaffoldState: ScaffoldState = rememberScaffoldState()
    val snackBarMessage = stringResource(R.string.users_screen_snackbar_loaded_message)

    LaunchedEffect(SIDE_EFFECTS_KEY) {
        effectFlow?.onEach { effect ->
            when (effect) {
                is UserContract.Effect.DataWasLoaded -> {
                    scaffoldState.snackbarHostState.showSnackbar(
                        message = snackBarMessage,
                        duration = SnackbarDuration.Short
                    )
                }
                is UserContract.Effect.Navigation.ToRepos -> onNavigationRequested(effect)
            }
        }?.collect()
    }

    Scaffold(
        scaffoldState = scaffoldState,
        topBar = { UsersTopBar() }
    ) {
        when {
            state.isLoading -> Progress()
            state.isError -> NetworkError { onEventSent(UserContract.Event.Retry) }
            else -> UsersList(users = state.users) { onEventSent(UserContract.Event.UserSelection(it)) }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun UsersScreenSuccessPreview() {
    val users = List(3) { buildUserPreview() }
    UsersScreen(
        state = UserContract.State(
            users = users,
            isLoading = false,
            isError = false,
        ),
        effectFlow = null,
        onEventSent = {},
        onNavigationRequested = {},
    )
}

@Preview(showBackground = true)
@Composable
fun UsersScreenErrorPreview() {
    UsersScreen(
        state = UserContract.State(
            users = emptyList(),
            isLoading = false,
            isError = true,
        ),
        effectFlow = null,
        onEventSent = {},
        onNavigationRequested = {},
    )
}
