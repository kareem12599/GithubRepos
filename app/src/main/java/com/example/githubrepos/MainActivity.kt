package com.example.githubrepos

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.example.githubrepos.navigation.GitHubReposNavHost
import com.example.core_designsystem.theme.GithubReposTheme
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            GitHubReposApp()
        }
    }
}


@Composable
fun GitHubReposApp() {
    GithubReposTheme {
        val navController: NavHostController = rememberNavController()
        Surface(
            modifier = Modifier.fillMaxSize(),
            color = MaterialTheme.colors.background
        ) {
            GitHubReposNavHost(
                navHostController = navController,
                startDestination = GithubReposScreens.Overview.name
            )

        }
    }
}


enum class GithubReposScreens {
    Overview, Details
}

