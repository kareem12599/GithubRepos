package com.example.githubrepos.data.remote

import androidx.paging.ExperimentalPagingApi
import androidx.paging.LoadType
import androidx.paging.PagingConfig
import androidx.paging.PagingState
import androidx.paging.RemoteMediator
import androidx.room.Room
import androidx.test.core.app.ApplicationProvider
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.example.githubrepos.data.database.RepoDatabase
import com.example.githubrepos.data.model.Repo
import com.example.githubrepos.ui.overview.fakeRepo
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runTest
import org.junit.After
import org.junit.Assert.*
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mock
import org.mockito.Mockito.`when`
import org.mockito.Mockito.anyInt
import org.mockito.MockitoAnnotations

@ExperimentalPagingApi
@OptIn(ExperimentalCoroutinesApi::class)
@RunWith(AndroidJUnit4::class)
class ReposRemoteMediatorTest {

    @Mock
    private lateinit var reposApi: GitHubRepoApi


    private val mockDb = Room.inMemoryDatabaseBuilder(
        ApplicationProvider.getApplicationContext(), RepoDatabase::class.java
    ).build()


    private lateinit var remoteMediator: ReposRemoteMediator

    @Before
    fun setUp() {
        MockitoAnnotations.openMocks(this)
        remoteMediator = ReposRemoteMediator(reposApi, mockDb)
    }

    @Test
    fun refreshLoadReturnsSuccessAndEndOfPaginationWhenDataIsEmpty() = runTest {
        `when`(reposApi.getRepos(1)).thenReturn(emptyList())
        val pagingState = PagingState<Int, Repo>(
            emptyList(),
            null,
            PagingConfig(10),
            10
        )
        val result = remoteMediator.load(LoadType.REFRESH, pagingState)
        assertTrue(result is RemoteMediator.MediatorResult.Success)
        assertTrue((result as RemoteMediator.MediatorResult.Success).endOfPaginationReached)
    }

    @Test
    fun refreshLoadReturnsSuccessAndPaginationNotEnded() = runTest {
        `when`(reposApi.getRepos(anyInt())).thenReturn(listOf(fakeRepo))
        val pagingState = PagingState<Int, Repo>(
            emptyList(),
            null,
            PagingConfig(10),
            10
        )
        val result = remoteMediator.load(LoadType.REFRESH, pagingState)
        assertTrue(result is RemoteMediator.MediatorResult.Success)
        assertFalse((result as RemoteMediator.MediatorResult.Success).endOfPaginationReached)
    }

    @Test
    fun refreshLoadReturnsErrorResultWhenErrorOccurs() = runTest {
        `when`(reposApi.getRepos(anyInt())).thenThrow(RuntimeException("404", Throwable()))
        val pagingState = PagingState<Int, Repo>(
            listOf(),
            null,
            PagingConfig(10),
            10
        )
        val result = remoteMediator.load(LoadType.REFRESH, pagingState)
        assertTrue(result is RemoteMediator.MediatorResult.Error)
    }


    @After
    fun tearDown() {
        mockDb.clearAllTables()

    }

}