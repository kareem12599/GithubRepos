package com.example.githubrepos.ui

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.cachedIn
import com.example.githubrepos.data.model.User
import com.example.githubrepos.data.repository.GithubRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class GithubReposViewModel @Inject constructor(repository: GithubRepository) : ViewModel() {

    val users = repository.fetchRepos().cachedIn(viewModelScope)

    var userItem by mutableStateOf<User?>(null)
        private set

    fun setUser(user: User) {
        userItem = user
    }

}