package com.example.githubrepos.data.repository

import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import com.example.githubrepos.data.model.User
import com.example.githubrepos.data.remote.ReposDataSource
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

interface GithubRepository {
    fun fetchRepos(): Flow<PagingData<User>>
}

class GithubRepositoryImp @Inject constructor(
    private val dataSource: ReposDataSource
) : GithubRepository {
    override fun fetchRepos(): Flow<PagingData<User>> {
        return Pager(PagingConfig(pageSize = NETWORK_PAGE_SIZE)) {
            dataSource
        }.flow
    }

    companion object {
        const val NETWORK_PAGE_SIZE = 10
    }
}