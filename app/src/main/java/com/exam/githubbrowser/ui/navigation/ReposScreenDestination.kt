package com.exam.githubbrowser.ui.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavController
import com.exam.githubbrowser.ui.feature.repo.ReposContract
import com.exam.githubbrowser.ui.feature.repo.ReposViewModel
import com.exam.githubbrowser.ui.feature.repo.composables.ReposScreen
import org.koin.androidx.compose.getViewModel
import org.koin.core.parameter.parametersOf

@Composable
fun ReposScreenDestination(UserId: String, navController: NavController) {
    val viewModel = getViewModel<ReposViewModel> { parametersOf(UserId) }
    ReposScreen(
        state = viewModel.viewState.value,
        effectFlow = viewModel.effect,
        onEventSent = { event -> viewModel.setEvent(event) },
        onNavigationRequested = { navigationEffect ->
            if (navigationEffect is ReposContract.Effect.Navigation.Back) {
                navController.popBackStack()
            }
        },
    )
}