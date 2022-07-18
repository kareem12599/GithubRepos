package com.example.githubrepos.data.repository

import androidx.paging.ExperimentalPagingApi
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import com.example.githubrepos.data.database.RepoDao
import com.example.githubrepos.data.model.Repo
import com.example.githubrepos.data.remote.ReposRemoteMediator
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

interface GithubRepository {
    fun fetchRepos(): Flow<PagingData<Repo>>
}

@OptIn(ExperimentalPagingApi::class)
class GithubRepositoryImp @Inject constructor(
    private val reposRemoteMediator: ReposRemoteMediator,
    private val repoDao: RepoDao
) : GithubRepository {
    override fun fetchRepos(): Flow<PagingData<Repo>> {
        return Pager(
            config = PagingConfig(pageSize = NETWORK_PAGE_SIZE),
            remoteMediator = reposRemoteMediator,
        ) {
            repoDao.getAllRepos()
        }.flow
    }

    companion object {
        const val NETWORK_PAGE_SIZE = 10
    }
}