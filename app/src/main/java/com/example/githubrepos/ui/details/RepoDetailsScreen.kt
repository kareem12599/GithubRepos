package com.example.githubrepos.ui.details

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Icon
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import coil.compose.rememberAsyncImagePainter
import com.example.core_designsystem.component.CTAButton
import com.example.core_designsystem.component.RepoDescriptionText
import com.example.core_designsystem.component.RepoIsPrivateSign
import com.example.core_designsystem.component.RepoUserFullName
import com.example.core_designsystem.component.RepoVisibilityText
import com.example.githubrepos.data.model.Repo

@Composable
fun RepoDetailsScreen(
    repoItem: Repo?,
    onBackIconClicked: () -> Unit,
) {
    repoItem?.let {
        ReposDetails(
            repo = it,
            onBackIconClicked = onBackIconClicked,
        )
    }


}

@Composable
fun ReposDetails(
    repo: Repo,
    onBackIconClicked: () -> Unit,
) {

    Column(
        modifier = Modifier.fillMaxSize()
    ) {
        Icon(
            imageVector = Icons.Filled.ArrowBack,
            contentDescription = "back-arrow",
            modifier = Modifier
                .padding(start = 16.dp, top = 16.dp)
                .clip(CircleShape)
                .clickable(onClick = onBackIconClicked)
                .padding(8.dp)
        )
        Spacer(modifier = Modifier.height(18.dp))
        Box(
            modifier = Modifier.padding(start = 16.dp, end = 16.dp)
                .fillMaxSize()
        ) {
            Column(
                horizontalAlignment = Alignment.CenterHorizontally,
                modifier = Modifier
                    .fillMaxSize()
            ) {

                Spacer(modifier = Modifier.height(18.dp))
                repo.owner?.let { RepoAvatarImageRoundedCorner(it.avatar_url) }
                Spacer(modifier = Modifier.height(8.dp))
                repo.full_name?.let { RepoUserFullName(it) }
                Spacer(modifier = Modifier.height(8.dp))
                repo.description?.let { RepoDescriptionText(it) }
                Spacer(modifier = Modifier.height(8.dp))
                repo.visibility?.let { RepoVisibilityText(it) }
                Spacer(modifier = Modifier.height(8.dp))
                repo.private?.let { RepoIsPrivateSign(it) }
                Spacer(modifier = Modifier.height(8.dp))
                repo.html_url?.let { CTAButton(it) }
            }


        }
    }
}

@Composable
fun RepoAvatarImageRoundedCorner(avatarUrl: String) {
    Image(
        painter = rememberAsyncImagePainter(model = avatarUrl),
        contentDescription = "user_avatar",
        modifier = Modifier
            .size(100.dp)
            .clip(RoundedCornerShape(12.dp))
            .background(Color.White)
    )
}


