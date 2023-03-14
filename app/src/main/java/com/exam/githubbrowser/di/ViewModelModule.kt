package com.exam.githubbrowser.di

import com.exam.githubbrowser.ui.feature.repo.ReposViewModel
import com.exam.githubbrowser.ui.feature.user.UserViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val viewModelModule = module {
    viewModel {
        UserViewModel(githubRepository = get())
    }

    viewModel { parameters ->
        ReposViewModel(
            userId = parameters.get(),
            githubRepository = get()
        )
    }
}
