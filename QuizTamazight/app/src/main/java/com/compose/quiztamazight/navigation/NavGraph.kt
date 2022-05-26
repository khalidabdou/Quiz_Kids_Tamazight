package com.example.testfriends_jetpackcompose.navigation

import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.compose.quiztamazight.screens.Home
import com.compose.quiztamazight.screens.Profile
import com.compose.quiztamazight.screens.Quiz
import com.compose.quiztamazight.screens.Results
import com.compose.quiztamazight.viewModels.viewModel


@ExperimentalAnimationApi
@Composable
fun SetupNavGraph(
    navController: NavHostController,
    startDestination: String,
    viewModel: viewModel,
) {
    NavHost(
        navController = navController,
        startDestination = startDestination
    ) {
        composable(route = Screen.Home.route) {
            Home(navController = navController, viewModel = viewModel)
        }

        composable(route = Screen.Quiz.route) {
            Quiz(navController = navController, viewModel = viewModel)
        }
        composable(route = Screen.Profile.route) {
            Profile()
        }

        composable(route = Screen.Results.route) {
            Results(navController = navController, viewModel = viewModel)
        }

    }
}