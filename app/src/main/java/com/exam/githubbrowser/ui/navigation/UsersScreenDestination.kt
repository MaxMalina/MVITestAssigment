package com.exam.githubbrowser.ui.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavController
import com.exam.githubbrowser.ui.feature.user.UserContract
import com.exam.githubbrowser.ui.feature.user.UserViewModel
import com.exam.githubbrowser.ui.feature.user.composables.UsersScreen
import org.koin.androidx.compose.getViewModel

@Composable
fun UsersScreenDestination(navController: NavController) {
    val viewModel = getViewModel<UserViewModel>()
    UsersScreen(
        state = viewModel.viewState.value,
        effectFlow = viewModel.effect,
        onEventSent = { event ->  viewModel.setEvent(event) },
        onNavigationRequested = { navigationEffect ->
            if (navigationEffect is UserContract.Effect.Navigation.ToRepos) {
                navController.navigateToRepos(navigationEffect.userId)
            }
        }
    )
}
