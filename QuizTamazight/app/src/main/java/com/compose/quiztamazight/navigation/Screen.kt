package com.example.testfriends_jetpackcompose.navigation

sealed class Screen(val route: String) {
    object Home : Screen(route = "Home")
    object Quiz : Screen(route = "Quiz")
    object Results : Screen(route = "Results")
    object Profile : Screen(route = "Profile")

}