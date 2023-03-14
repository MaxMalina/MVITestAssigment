package com.exam.githubbrowser.di

import com.exam.githubbrowser.data.GithubApi
import com.exam.githubbrowser.data.GithubRepository
import com.exam.githubbrowser.data.GithubRepositoryImpl
import com.exam.githubbrowser.data.Urls
import okhttp3.OkHttpClient
import org.koin.dsl.module
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

val appModule = module {

    single {
        OkHttpClient.Builder().build()
    }

    single {
        Retrofit.Builder()
            .baseUrl(Urls.BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .client(get())
            .build()
    }

    single {
        get<Retrofit>().create(GithubApi::class.java)
    }

    factory<GithubRepository> {
        GithubRepositoryImpl(
            githubApi = get()
        )
    }
}

val appModules = listOf(
    appModule,
    viewModelModule,
)
