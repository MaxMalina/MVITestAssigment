package com.exam.githubbrowser.data

import com.exam.githubbrowser.data.model.Repo
import com.exam.githubbrowser.data.model.User
import com.exam.githubbrowser.data.model.UserDetails
import kotlinx.coroutines.runBlocking
import okhttp3.OkHttpClient
import okhttp3.mockwebserver.MockWebServer
import org.junit.After
import org.junit.Before
import org.junit.Test
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit
import kotlin.test.assertEquals

class GithubApiTest {

    private val mockWebService = MockWebServer()

    private val client =
        OkHttpClient.Builder()
            .connectTimeout(30, TimeUnit.SECONDS)
            .readTimeout(30, TimeUnit.SECONDS)
            .writeTimeout(30, TimeUnit.SECONDS)
            .build()

    private val api = Retrofit.Builder()
        .baseUrl(mockWebService.url("/"))
        .client(client)
        .addConverterFactory(GsonConverterFactory.create())
        .build()
        .create(GithubApi::class.java)

    @Before
    fun setup() {
        mockWebService.start(8080)
    }

    @After
    fun tearDown() {
        mockWebService.shutdown()
    }

    @Test
    fun `when fetching users then returns users correctly`() {
        mockWebService.enqueueResponse(
            fileName = "users.json", code = 200
        )
        val expected = listOf(
            User(
                userId = "mojombo",
                avatarUrl = "https://avatars.githubusercontent.com/u/1?v=4",
                htmlUrl = "https://github.com/mojombo",
            ),
            User(
                userId = "defunkt",
                avatarUrl = "https://avatars.githubusercontent.com/u/2?v=4",
                htmlUrl = "https://github.com/defunkt",
            ),
            User(
                userId = "pjhyett",
                avatarUrl = "https://avatars.githubusercontent.com/u/3?v=4",
                htmlUrl = "https://github.com/pjhyett",
            ),
        )

        val actual = runBlocking { api.getUsers() }
        val request = mockWebService.takeRequest()

        assertEquals(expected, actual)
        assertEquals("/users", request.path)
    }

    @Test
    fun `when fetching user then returns user correctly`() {
        val userId = "mojombo"
        mockWebService.enqueueResponse(
            fileName = "user-detail.json", code = 200
        )
        val expected = UserDetails(
            avatarUrl = "https://avatars.githubusercontent.com/u/1?v=4",
            htmlUrl = "https://github.com/mojombo",
            name = "Tom Preston-Werner",
            location = "San Francisco",
            blogUrl = "http://tom.preston-werner.com",
            publicRepos = 64,
            followers = 22991,
            following = 11,
        )

        val actual = runBlocking { api.getUser(userId) }
        val request = mockWebService.takeRequest()

        assertEquals(expected, actual)
        assertEquals("/users/$userId", request.path)
    }

    @Test
    fun `when fetching repos then returns repos correctly`() {
        val userId = "mojombo"
        mockWebService.enqueueResponse(
            fileName = "repos.json", code = 200
        )

        val expected = listOf(
            Repo(
                id = 26899533,
                name = "30daysoflaptops.github.io",
                description = null,
                watchersCount = 7,
                forksCount = 2,
                stargazersCount = 7,
                language = "CSS",
                htmlUrl = "https://github.com/mojombo/30daysoflaptops.github.io",
            ),
            Repo(
                id = 17358646,
                name = "asteroids",
                description = "Destroy your Atom editor, Asteroids style!",
                watchersCount = 93,
                forksCount = 13,
                stargazersCount = 93,
                language = "JavaScript",
                htmlUrl = "https://github.com/mojombo/asteroids",
            ),
            Repo(
                id = 29941343,
                name = "benbalter.github.com",
                description = "The personal website of Ben Balter. Built using Jekyll and GitHub Pages. See humans.txt for more infos.",
                watchersCount = 5,
                forksCount = 7,
                stargazersCount = 5,
                language = "CSS",
                htmlUrl = "https://github.com/mojombo/benbalter.github.com",
            ),
        )

        val actual = runBlocking { api.getRepos(userId) }
        val request = mockWebService.takeRequest()

        assertEquals(expected, actual)
        assertEquals("/users/$userId/repos", request.path)
    }
}