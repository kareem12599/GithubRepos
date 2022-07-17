package com.example.githubrepos.data.remote

import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.example.githubrepos.data.model.Repo

class ReposDataSource(private val apiClient: GitHubRepoApi) : PagingSource<Int, Repo>() {

    override fun getRefreshKey(state: PagingState<Int, Repo>): Int? {
        return state.anchorPosition
    }

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, Repo> {
        return try {
            val nextPage = params.key ?: 1
            val userList = apiClient.getRepos(page = nextPage)
            LoadResult.Page(
                data = userList,
                prevKey = if (nextPage == 1) null else nextPage - 1,
                nextKey = if (userList.isEmpty()) null else nextPage + 1
            )
        } catch (exception: Exception) {
            return LoadResult.Error(exception)
        }
    }
}