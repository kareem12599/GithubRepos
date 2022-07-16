package com.example.githubrepos.ui.details

import androidx.compose.ui.test.assertIsDisplayed
import androidx.compose.ui.test.junit4.createComposeRule
import androidx.compose.ui.test.onNodeWithContentDescription
import androidx.compose.ui.test.onNodeWithText
import com.example.githubrepos.ui.overview.fakeUser
import dagger.hilt.android.testing.HiltAndroidRule
import dagger.hilt.android.testing.HiltAndroidTest
import org.junit.Before
import org.junit.Rule
import org.junit.Test

@HiltAndroidTest
class RepoDetailsScreenKtTest {
    @get:Rule(order = 1)
    val hiltRule = HiltAndroidRule(this)

    @get:Rule(order = 2)
    val composeTestRule = createComposeRule()

    @Before
    fun setUp() {
        hiltRule.inject()

    }

    @Test
    fun gitHubRepoDetailsScreenTest() {
        composeTestRule.setContent {
            ReposDetails(
                fakeUser, {}
            )
        }
        composeTestRule.onNodeWithContentDescription("back-arrow").assertIsDisplayed()
        composeTestRule.onNodeWithContentDescription("user_avatar").assertExists()
        composeTestRule.onNodeWithText("full_name").assertIsDisplayed()
        composeTestRule.onNodeWithText("public").assertIsDisplayed()
        composeTestRule.onNodeWithText("✅").assertIsDisplayed()

    }
}