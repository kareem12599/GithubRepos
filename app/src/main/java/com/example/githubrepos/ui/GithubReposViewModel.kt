package com.example.githubrepos.ui

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.cachedIn
import com.example.githubrepos.data.model.Repo
import com.example.githubrepos.data.repository.GithubRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class GithubReposViewModel @Inject constructor(repository: GithubRepository) : ViewModel() {

    val users = repository.fetchRepos().cachedIn(viewModelScope)

    var repoItem by mutableStateOf<Repo?>(null)
        private set

    fun setUser(repo: Repo) {
        repoItem = repo
    }

}