package com.example.githubrepos.data.database

import androidx.paging.PagingSource
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.githubrepos.data.model.Repo

@Dao
interface RepoDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAll(repos: List<Repo>)


    @Query("select * from repos")
    fun getAllRepos(): PagingSource<Int, Repo>

    @Query("delete from repos")
    suspend fun clearRepos()
}