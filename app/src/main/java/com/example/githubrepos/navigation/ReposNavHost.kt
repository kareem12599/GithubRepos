package com.example.githubrepos.navigation

import androidx.compose.runtime.Composable
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.example.githubrepos.GithubReposScreens
import com.example.githubrepos.ui.GithubReposViewModel
import com.example.githubrepos.ui.details.RepoDetailsScreen
import com.example.githubrepos.ui.overview.GithubReposScreen

@Composable
fun GitHubReposNavHost(
    navHostController: NavHostController,
    startDestination: String,
    viewModel: GithubReposViewModel = hiltViewModel()
) {
    NavHost(
        navController = navHostController,
        startDestination = startDestination
    ) {
        ReposGraph(
            viewModel = viewModel,
            navigateToDetails = { navHostController.navigate(GithubReposScreens.Details.name) },
            navigateBackToOverView = { navHostController.navigateUp() }
        )

    }
}

fun NavGraphBuilder.ReposGraph(
    viewModel: GithubReposViewModel,
    navigateToDetails: () -> Unit,
    navigateBackToOverView: () -> Unit
) {
    composable(GithubReposScreens.Overview.name) {
        GithubReposScreen(
            viewModel = viewModel,
            onItemClicked = navigateToDetails
        )
    }
    composable(
        route = GithubReposScreens.Details.name
    ) {
        RepoDetailsScreen(
            userItem = viewModel.userItem,
            onBackIconClicked = navigateBackToOverView
        )
    }

}
