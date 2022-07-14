package com.example.githubrepos.data.remote

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.paging.PagingSource
import com.example.githubrepos.data.model.Owner
import com.example.githubrepos.data.model.User
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runTest
import org.junit.Assert.*
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.mockito.BDDMockito.given
import org.mockito.Mock
import org.mockito.MockitoAnnotations
import org.mockito.kotlin.any

@OptIn(ExperimentalCoroutinesApi::class)
class ReposDataSourceTest {
    @get:Rule
    val instantExecutorRule = InstantTaskExecutorRule()

    @Mock
    lateinit var api: GitHubRepoApi

    lateinit var reposDataSource: ReposDataSource

    private val fakeUser = User(
        name = "Karim",
        full_name = "",
        owner = Owner(""),
        private = false,
        visibility = "public"
    )


    @Before
    fun setup() {
        MockitoAnnotations.openMocks(this)
        reposDataSource = ReposDataSource(api)
    }


    @Test
    fun `when loading pages fails, paging data source propagates error `() = runTest {
        val error = RuntimeException("404", Throwable())
        given(api.getRepos(any())).willThrow(error)
        val expectedResult = PagingSource.LoadResult.Error<Int, User>(error)
        assertEquals(
            expectedResult, reposDataSource.load(
                PagingSource.LoadParams.Refresh(
                    key = 0,
                    loadSize = 1,
                    placeholdersEnabled = false
                )
            )
        )
    }

    @Test
    fun `when refreshing the first element in  source page, data loaded and returns success`() =
        runTest {
            given(api.getRepos(any())).willReturn(listOf(fakeUser))
            val expectedResult = PagingSource.LoadResult.Page(
                data = listOf(fakeUser),
                prevKey = null,
                nextKey = 2
            )
            assertEquals(
                expectedResult, reposDataSource.load(
                    PagingSource.LoadParams.Refresh(
                        key = 1,
                        loadSize = 10,
                        placeholdersEnabled = false
                    )
                )
            )
        }

    @Test
    fun `when appending an element into repos page, new key updated and return success`() =
        runTest {
            given(api.getRepos(any())).willReturn(listOf(fakeUser))
            val expectedResult = PagingSource.LoadResult.Page(
                data = listOf(fakeUser),
                prevKey = 2,
                nextKey = 4
            )
            assertEquals(
                expectedResult, reposDataSource.load(
                    PagingSource.LoadParams.Append(
                        key = 3,
                        loadSize = 10,
                        placeholdersEnabled = false
                    )
                )
            )
        }

}