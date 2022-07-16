package com.example.githubrepos.data.di

import androidx.paging.PagingData
import com.example.githubrepos.data.model.User
import com.example.githubrepos.data.repository.GithubRepository
import com.example.githubrepos.data.repository.GithubRepositoryImp
import com.example.githubrepos.ui.overview.fakeUser
import dagger.Binds
import dagger.Module
import dagger.hilt.android.components.ActivityRetainedComponent
import dagger.hilt.android.scopes.ActivityRetainedScoped
import dagger.hilt.testing.TestInstallIn
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

@Module
@TestInstallIn(
    components = [ActivityRetainedComponent::class],
    replaces = [GithubRepoBindModule::class]
)
abstract class AppTestModule {

    @ActivityRetainedScoped
    @Binds
    abstract fun bindGithubRepository(repositoryImpl: FakeGithubRepository): GithubRepository

    class FakeGithubRepository @Inject constructor() : GithubRepository {
        override fun fetchRepos(): Flow<PagingData<User>> {
            return flow {
                PagingData.from(listOf(fakeUser))
            }
        }

    }
}