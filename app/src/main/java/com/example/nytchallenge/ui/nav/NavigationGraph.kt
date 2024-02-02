package com.example.nytchallenge.ui.nav

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import com.example.nytchallenge.ui.home.HomeDestination
import com.example.nytchallenge.ui.home.HomeScreen
import com.example.nytchallenge.ui.task.TaskEditDestination
import com.example.nytchallenge.ui.task.TaskEditScreen
import com.example.nytchallenge.ui.task.TaskEntryDestination
import com.example.nytchallenge.ui.task.TaskEntryScreen

/**
 * Provide Navigation Graph for application.
 */

@Composable
fun NavigationHost(
    navController: NavHostController,
    modifier: Modifier = Modifier
) {
    NavHost(
        navController = navController,
        startDestination = HomeDestination.route,
        modifier = modifier
    ) {
        /**
         * Add composable for each destination in the app
         */

        /**
         * Home Destination
         */
        composable(route = HomeDestination.route) {
            HomeScreen(
                navigateToTaskEntry = { navController.navigate(TaskEntryDestination.route)},
                navigateToTaskEdit = { navController.navigate("${TaskEditDestination.route}/$it")}
            )
        }
        /**
         * Task Entry Destination
         */
        composable(route = TaskEntryDestination.route) {
            TaskEntryScreen(
                navigateBack = { navController.popBackStack() },
                onNavigateUp = { navController.navigateUp() }
            )
        }
        /**
         * Task Edit Destination
         */
        composable(
            route = TaskEditDestination.routeWithArgs,
            arguments = listOf(navArgument(TaskEditDestination.taskIdArg) {
                type = NavType.IntType
            })
        ) {
            TaskEditScreen(
                navigateBack = { navController.popBackStack() },
                onNavigateUp = { navController.navigateUp() }
            )
        }



    }

}