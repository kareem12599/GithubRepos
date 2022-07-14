package com.example.githubrepos.data.remote

import com.example.githubrepos.data.model.User
import retrofit2.http.GET
import retrofit2.http.Query

interface GitHubRepoApi {
    @GET("repos")
    suspend fun getRepos(@Query("page") page: Int): List<User>
}