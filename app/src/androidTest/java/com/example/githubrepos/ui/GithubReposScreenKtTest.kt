package com.example.githubrepos.ui

import androidx.compose.ui.test.assertIsDisplayed
import androidx.compose.ui.test.junit4.createComposeRule
import androidx.compose.ui.test.onNodeWithTag
import androidx.paging.PagingData
import androidx.paging.compose.collectAsLazyPagingItems
import com.example.githubrepos.data.model.Owner
import com.example.githubrepos.data.model.User
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
    fun GetHubReposTest() {
        composeTestRule.setContent {
            GithubRepos(
                lazyPagingItems = fakePagingData.collectAsLazyPagingItems()
            )

        }
        composeTestRule.onNodeWithTag("loading view").assertIsDisplayed()
    }


}

val fakePagingData = flow<PagingData<User>> {
    PagingData.from(
        listOf(
            User(
                name = "Karim",
                full_name = "",
                owner = Owner(""),
                private = false,
                visibility = "public"
            )
        )
    )
}
