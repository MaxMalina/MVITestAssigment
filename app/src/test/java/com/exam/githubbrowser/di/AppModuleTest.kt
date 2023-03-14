package com.exam.githubbrowser.di

import com.exam.githubbrowser.ui.feature.repo.ReposViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.setMain
import org.junit.After
import org.junit.Before
import org.junit.Test
import org.koin.core.context.stopKoin
import org.koin.dsl.koinApplication
import org.koin.test.check.checkModules

class AppModuleTest {

    @Before
    fun setup() {
        Dispatchers.setMain(Dispatchers.Unconfined)
    }

    @After
    fun tearDown() {
        Dispatchers.resetMain()
        stopKoin()
    }

    @Test
    fun `verify koin app`() {
        koinApplication {
            modules(appModule, viewModelModule)
            checkModules {
                withParameter<ReposViewModel> { "userId" }
            }
        }
    }
}