package com.example.githubrepos.data.di

import android.content.Context
import androidx.room.Room
import com.example.githubrepos.data.database.RepoDao
import com.example.githubrepos.data.database.RepoDatabase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class DatabaseModule {
    @Singleton
    @Provides
    fun provideReposDataBase(@ApplicationContext context: Context): RepoDatabase {
        return Room.databaseBuilder(
            context,
            RepoDatabase::class.java, "github-repos.db"
        )
            .build()

    }

    @Provides
    fun provideRepoDao(database: RepoDatabase): RepoDao = database.reposDao()
}