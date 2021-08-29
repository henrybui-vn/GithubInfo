package com.android.githubinfo

import com.android.githubinfo.di.apiModule
import com.android.githubinfo.net.APIService
import com.android.githubinfo.utils.BASE_URL
import io.reactivex.schedulers.Schedulers
import org.junit.Test

import org.junit.Assert.*
import org.junit.Before
import org.koin.core.context.startKoin
import org.koin.java.KoinJavaComponent

/**
 * Example local unit test, which will execute on the development machine (host).
 *
 * See [testing documentation](http://d.android.com/tools/testing).
 */
class ExampleUnitTest {
    private val apiService: APIService by KoinJavaComponent.inject(APIService::class.java)

    @Before
    fun init() {
        startKoin {
            modules(apiModule)
        }
    }

    @Test
    fun testAPIService() {
        val url = "/users/android"
        apiService.getUserInfo(url).subscribe(
            { result ->
                assertNotNull(result)
                assertNotNull(result.id)
                assertNotEquals(result.id, "")
                testProjectsRepo(result.repos_url)
            },
            { error ->
                assertEquals(error.message, "")
            }
        )
    }

    fun testProjectsRepo(projectsRepo: String) {
        apiService.getProjectsInfo(projectsRepo.replace(BASE_URL, "")).subscribe(
            { result ->
                assertNotNull(result)
                assertNotEquals(result.size, 0)
            },
            { error ->
                assertEquals(error.message, "")
            }
        )
    }
}