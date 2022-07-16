package com.example.core_designsystem.component

import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextOverflow


@Composable
fun RepoNameText(name: String) {
    Text(
        text = name,
        maxLines = 1,
        style = MaterialTheme.typography.h6,
        overflow = TextOverflow.Ellipsis
    )
}


@Composable
fun RepoUserFullName(fullName: String) {
    Text(
        text = fullName,
        style = MaterialTheme.typography.h6,
        color = Color.Black
    )
}

@Composable
fun RepoDescriptionText(description: String) {

    Text(
        text = description,
        style = MaterialTheme.typography.h6,
        color = Color.Gray
    )
}

@Composable
fun RepoVisibilityText(visibility: String) {
    Text(
        text = visibility,
        style = MaterialTheme.typography.subtitle1,
        color = Color.Gray
    )
}

@Composable
fun RepoIsPrivateSign(isPrivate: Boolean) {
    Text(
        text = if (isPrivate) "❌" else "✅",
        maxLines = 2,
        style = MaterialTheme.typography.subtitle2,
        overflow = TextOverflow.Ellipsis
    )
}
