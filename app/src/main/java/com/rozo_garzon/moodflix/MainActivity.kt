package com.rozo_garzon.moodflix

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.runtime.Composable
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.rozo_garzon.moodflix.ui.detail.DetailScreen
import com.rozo_garzon.moodflix.ui.detail.DetailViewModel
import com.rozo_garzon.moodflix.ui.home.HomeScreen
import com.rozo_garzon.moodflix.ui.home.HomeViewModel
import com.rozo_garzon.moodflix.ui.login.LoginScreen
import com.rozo_garzon.moodflix.ui.theme.MoodFlixTheme
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            MoodFlixTheme {
                AppNavigation()
            }
        }
    }
}

@Composable
fun AppNavigation() {
    val navController = rememberNavController()

    NavHost(navController = navController, startDestination = "login") {
        composable("login") {
            LoginScreen(
                onLoginSuccess = {
                    navController.navigate("home") {
                        popUpTo("login") { inclusive = true }
                    }
                }
            )
        }
        composable("home") {
            val viewModel: HomeViewModel = hiltViewModel()
            HomeScreen(
                viewModel = viewModel,
                onMovieClick = { movieId ->
                    navController.navigate("detail/$movieId")
                }
            )
        }
        composable(
            route = "detail/{movieId}",
            arguments = listOf(navArgument("movieId") { type = NavType.IntType })
        ) { backStackEntry ->
            val movieId = backStackEntry.arguments?.getInt("movieId") ?: return@composable
            val viewModel: DetailViewModel = hiltViewModel()
            DetailScreen(
                movieId = movieId,
                viewModel = viewModel,
                onBack = { navController.popBackStack() }
            )
        }
    }
}
