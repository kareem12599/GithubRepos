package com.example.githubrepos.data.remote

import androidx.paging.ExperimentalPagingApi
import androidx.paging.LoadType
import androidx.paging.PagingState
import androidx.paging.RemoteMediator
import androidx.room.withTransaction
import com.example.githubrepos.data.database.RemoteKeys
import com.example.githubrepos.data.database.RepoDatabase
import com.example.githubrepos.data.model.Repo


@OptIn(ExperimentalPagingApi::class)
class ReposRemoteMediator(
    private val api: GitHubRepoApi,
    private val database: RepoDatabase
) : RemoteMediator<Int, Repo>() {

    override suspend fun load(loadType: LoadType, state: PagingState<Int, Repo>): MediatorResult {

        val pageToBeLoaded = when (loadType) {
            LoadType.REFRESH -> {
                STARTING_PAGE_INDEX
            }
            LoadType.PREPEND -> {
                return MediatorResult.Success(endOfPaginationReached = false)
            }
            LoadType.APPEND -> {
                val remoteKeys =
                    state.pages.lastOrNull { it.data.isNotEmpty() }?.data?.lastOrNull()?.id?.let {
                        database.remoteKeysDao().remoteKeysRepoId(it)
                    }
                val nextKey = remoteKeys?.nextKey
                    ?: return MediatorResult.Success(endOfPaginationReached = true)
                nextKey
            }
        }


        try {
            val repos = api.getRepos(pageToBeLoaded)
            val endOfPaginationReached = repos.isEmpty()
            database.withTransaction {
                if (loadType == LoadType.REFRESH) {
                    database.remoteKeysDao().clearRemoteKeys()
                    database.reposDao().clearRepos()
                }
                val nextKey = if (endOfPaginationReached) null else pageToBeLoaded + 1
                val keys = repos.map {
                    RemoteKeys(repoId = it.id, nextKey = nextKey)
                }
                database.remoteKeysDao().insertAll(keys)
                database.reposDao().insertAll(repos)
            }
            return MediatorResult.Success(endOfPaginationReached = endOfPaginationReached)
        } catch (exception: Exception) {
            return MediatorResult.Error(exception)
        }
    }

    companion object {
        const val STARTING_PAGE_INDEX = 1
    }
}
