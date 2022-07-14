package com.example.githubrepos.data.di

import com.example.githubrepos.BuildConfig
import com.example.githubrepos.data.remote.GitHubRepoApi
import com.example.githubrepos.data.remote.ReposDataSource
import com.google.gson.Gson
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object NetworkModule {

    @Singleton
    @Provides
    fun provideOkHttpLoggingInterceptor(): OkHttpClient {
        return OkHttpClient.Builder().addInterceptor(
            HttpLoggingInterceptor().apply {
                level = HttpLoggingInterceptor.Level.BODY
            }
        ).build()
    }

    @Singleton
    @Provides
    fun provideFoodApi(okHttpClient: OkHttpClient): GitHubRepoApi {
        return Retrofit.Builder()
            .baseUrl(BuildConfig.BASE_URL)
            .addConverterFactory(GsonConverterFactory.create(Gson()))
            .client(okHttpClient)
            .build()
            .create(GitHubRepoApi::class.java)
    }

    @Singleton
    @Provides
    fun provideReposDataSource(api: GitHubRepoApi) = ReposDataSource(api)
}