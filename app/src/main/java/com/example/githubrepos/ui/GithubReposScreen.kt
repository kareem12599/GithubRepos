package com.example.githubrepos.ui

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.material.MaterialTheme
import androidx.compose.material.OutlinedButton
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.paging.LoadState
import androidx.paging.compose.LazyPagingItems
import androidx.paging.compose.collectAsLazyPagingItems
import androidx.paging.compose.items
import coil.compose.rememberAsyncImagePainter
import com.example.githubrepos.data.model.User

@Composable
fun GithubReposScreen(
    modifier: Modifier = Modifier,
    viewModel: GithubReposViewModel = hiltViewModel(),
) {

    GithubRepos(
        lazyPagingItems = viewModel.users.collectAsLazyPagingItems(),
        modifier = modifier
    )
}


@Composable
fun GithubRepos(lazyPagingItems: LazyPagingItems<User>, modifier: Modifier = Modifier) {
    LazyColumn(
        modifier = modifier
    ) {

        if (lazyPagingItems.loadState.refresh == LoadState.Loading) {
            item { LoadingItem(modifier = Modifier.fillParentMaxSize()) }
        }

        items(lazyPagingItems) { item ->
            item?.let {
                GitHubItem(user = it) {
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
fun GitHubItem(user: User, onClick: () -> Unit) {
    Box(
        modifier = Modifier
            .padding(12.dp)
            .clip(RoundedCornerShape(28.dp))
            .fillMaxWidth()
            .background(Color.Cyan)
            .clickable { onClick() }

    ){

        Row(
            modifier = Modifier
                .padding(start = 16.dp, end = 16.dp)
                .fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Image(
                painter = rememberAsyncImagePainter(user.owner.avatar_url),
                contentDescription = "",
                modifier = Modifier
                    .size(40.dp)
                    .clip(CircleShape)
                    .background(Color.White)
            )

            Column(modifier = Modifier.padding(24.dp)) {

                Text(
                    text = user.name,
                    maxLines = 2,
                    style = MaterialTheme.typography.h6,
                    overflow = TextOverflow.Ellipsis
                )
                Text(
                    text = user.visibility,
                    maxLines = 2,
                    style = MaterialTheme.typography.subtitle1,
                    overflow = TextOverflow.Ellipsis
                )
                Text(
                    text = if (user.private) "❌" else "✅",
                    maxLines = 2,
                    style = MaterialTheme.typography.subtitle2,
                    overflow = TextOverflow.Ellipsis
                )

            }

        }
    }

}


@Preview(showBackground = true)
@Composable
fun LoadingItem(modifier: Modifier = Modifier) {
    CircularProgressIndicator(
        modifier = modifier.fillMaxWidth()
            .padding(16.dp)
            .wrapContentWidth(Alignment.CenterHorizontally)
            .testTag("loading view")


    )
}

@Composable
fun ErrorItem(
    message: String,
    modifier: Modifier = Modifier,
    onClickRetry: () -> Unit
) {
    Row(
        modifier = modifier.padding(16.dp),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Text(
            text = message,
            maxLines = 1,
            modifier = Modifier.weight(1f),
            style = MaterialTheme.typography.h6,
            color = Color.Green
        )
        OutlinedButton(onClick = onClickRetry) {
            Text(text = "Try again")
        }
    }
}

