package com.example.githubrepos.ui.overview

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.semantics.contentDescription
import androidx.compose.ui.semantics.semantics
import androidx.compose.ui.unit.dp
import androidx.paging.LoadState
import androidx.paging.compose.LazyPagingItems
import androidx.paging.compose.collectAsLazyPagingItems
import androidx.paging.compose.items
import coil.compose.rememberAsyncImagePainter
import com.example.core_designsystem.component.ErrorItem
import com.example.core_designsystem.component.LoadingItem
import com.example.core_designsystem.component.RepoIsPrivateSign
import com.example.core_designsystem.component.RepoNameText
import com.example.core_designsystem.component.RepoVisibilityText
import com.example.githubrepos.data.model.Repo
import com.example.githubrepos.ui.GithubReposViewModel

@Composable
fun GithubReposScreen(
    modifier: Modifier = Modifier,
    viewModel: GithubReposViewModel,
    onItemClicked: () -> Unit
) {

    GithubRepos(
        lazyPagingItems = viewModel.users.collectAsLazyPagingItems(),
        modifier = modifier,
        onItemClicked = {
            viewModel.setUser(it)
            onItemClicked()
        }
    )
}


@Composable
fun GithubRepos(
    lazyPagingItems: LazyPagingItems<Repo>, modifier: Modifier = Modifier,
    onItemClicked: (Repo) -> Unit
) {
    LazyColumn(
        modifier = modifier.semantics { contentDescription = "Overview Screen" }
    ) {

        if (lazyPagingItems.loadState.refresh == LoadState.Loading) {
            item { LoadingItem(modifier = Modifier.fillParentMaxSize()) }
        }

        items(lazyPagingItems) { item ->
            item?.let {
                GitHubItem(repo = it) {
                    onItemClicked(it)
                }
            }
        }
        with(lazyPagingItems) {
            when {
                loadState.append == LoadState.Loading -> {
                    item { LoadingItem() }
                }
                loadState.refresh is LoadState.Error -> {
                    val e = lazyPagingItems.loadState.refresh as LoadState.Error
                    item {
                        ErrorItem(
                            message = e.error.localizedMessage!!,
                            modifier = Modifier.fillParentMaxSize(),
                            onClickRetry = { retry() }
                        )
                    }
                }
            }
        }
    }
}

@Composable
fun GitHubItem(repo: Repo, onClick: () -> Unit = {}) {
    Box(
        modifier = Modifier
            .padding(12.dp)
            .clip(RoundedCornerShape(28.dp))
            .fillMaxWidth()
            .background(Color.Cyan)
            .clickable { onClick() }
            .semantics { contentDescription = "repos_item" }

    ) {

        Row(
            modifier = Modifier
                .padding(start = 16.dp, end = 16.dp)
                .fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically
        ) {
            repo.owner?.let { RepoAvatarImageCircleShape(it.avatar_url) }

            Column(modifier = Modifier.padding(24.dp)) {

                repo.name?.let { RepoNameText(it) }
                repo.visibility?.let { RepoVisibilityText(repo.visibility) }
                repo.private?.let { RepoIsPrivateSign(it) }

            }

        }
    }

}

@Composable
fun RepoAvatarImageCircleShape(avatarUrl: String) {
    Image(
        painter = rememberAsyncImagePainter(avatarUrl),
        contentDescription = "",
        modifier = Modifier
            .size(40.dp)
            .clip(CircleShape)
            .background(Color.White)
    )
}







