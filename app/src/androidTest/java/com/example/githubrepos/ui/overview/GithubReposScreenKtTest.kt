package com.example.githubrepos.ui.overview

import androidx.compose.ui.test.assertIsDisplayed
import androidx.compose.ui.test.junit4.createComposeRule
import androidx.compose.ui.test.onNodeWithTag
import androidx.compose.ui.test.onNodeWithText
import androidx.paging.PagingData
import androidx.paging.compose.collectAsLazyPagingItems
import com.example.githubrepos.data.model.Owner
import com.example.githubrepos.data.model.Repo
import dagger.hilt.android.testing.HiltAndroidRule
import dagger.hilt.android.testing.HiltAndroidTest
import kotlinx.coroutines.flow.flow
import org.junit.Before
import org.junit.Rule
import org.junit.Test

@HiltAndroidTest
class GithubReposScreenKtTest {
    @get:Rule(order = 1)
    val hiltRule = HiltAndroidRule(this)

    @get:Rule(order = 2)
    val composeTestRule = createComposeRule()

    @Before
    fun setUp() {
        hiltRule.inject()

    }

    @Test
    fun gitHubReposScreenTest() {
        composeTestRule.setContent {
            GithubRepos(
                lazyPagingItems = fakePagingData.collectAsLazyPagingItems(),
                onItemClicked = {}
            )

        }
        composeTestRule.onNodeWithTag("loading view").assertIsDisplayed()
    }

    @Test
    fun githubReposItemTest() {
        composeTestRule.setContent {
            GitHubItem(fakeRepo)
        }
        composeTestRule.onNodeWithText("user_name").assertIsDisplayed()
        composeTestRule.onNodeWithText("public").assertIsDisplayed()
        composeTestRule.onNodeWithText("✅").assertIsDisplayed()
    }


}

val fakePagingData = flow<PagingData<Repo>> {
    PagingData.from(
        listOf(
            fakeRepo
        )
    )
}
val fakeRepo =
    Repo(
        id = 12,
        name = "user_name",
        full_name = "full_name",
        owner = Owner(""),
        private = false,
        visibility = "public",
        description = "",
        html_url = ""
    )
