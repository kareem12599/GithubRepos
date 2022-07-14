package com.example.githubrepos.data.di

import com.example.githubrepos.data.repository.GithubRepository
import com.example.githubrepos.data.repository.GithubRepositoryImp
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ActivityRetainedComponent
import dagger.hilt.android.scopes.ActivityRetainedScoped

@Module
@InstallIn(ActivityRetainedComponent::class)
abstract class GithubRepoBindModule {

    @ActivityRetainedScoped
    @Binds
    abstract fun bindGithubRepository(repositoryImpl: GithubRepositoryImp): GithubRepository
}
