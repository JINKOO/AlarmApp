package com.jinkweonko.alarm.navigation

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import androidx.navigation.navDeepLink
import com.jinkweonko.alarm.detail.DetailScreen
import com.jinkweonko.alarm.detail.DetailViewModel
import com.jinkweonko.alarm.home.HomeScreen
import com.jinkweonko.alarm.home.HomeViewModel
import com.jinkweonko.alarm.reminder.ReminderScreen

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
                viewModel = hiltViewModel<HomeViewModel>(),
                navigateToDetail = {
                    navController.navigateWithSingleTop("alarm_detail/$it")
                }
            )
        }

        composable(
            route = AlarmDetail.route,
            arguments = listOf(navArgument("alarmId") { type = NavType.IntType })
        ) {
            val reminderId = it.arguments?.getInt("alarmId")
            DetailScreen(
                viewModel = hiltViewModel<DetailViewModel>(),
                navigateUp = navController::navigateUp
            )
        }

        composable(
            route = Alarm.route,
            arguments = listOf(navArgument("alarmId") { type = NavType.IntType }),
            deepLinks = listOf(
                navDeepLink {
                    uriPattern = "reminders://alarm/{alarmId}"
                }
            )
        ) { backStackEntry ->
            ReminderScreen()
        }
    }
}

fun NavHostController.navigateWithSingleTop(route: String) {
    this.navigate(route) {
        launchSingleTop = true
    }
}