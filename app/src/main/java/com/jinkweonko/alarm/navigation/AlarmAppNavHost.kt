package com.jinkweonko.alarm.navigation

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.jinkweonko.alarm.detail.DetailScreen
import com.jinkweonko.alarm.detail.DetailViewModel
import com.jinkweonko.alarm.home.HomeScreen

@Composable
fun AlarmAppNavHost() {
    val navController = rememberNavController()
    NavHost(
        modifier = Modifier.fillMaxSize(),
        navController = navController,
        startDestination = Home.route
    ) {
        composable(Home.route) {
            HomeScreen(
                navigateToDetail = {
                    navController.navigateWithSingleTop(AlarmDetail.route)
                }
            )
        }

        composable(AlarmDetail.route) {
            DetailScreen(
                viewModel = hiltViewModel<DetailViewModel>(),
                navigateUp = navController::navigateUp
            )
        }
    }
}

fun NavHostController.navigateWithSingleTop(route: String) {
    this.navigate(route) {
        launchSingleTop = true
    }
}