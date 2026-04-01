package com.sohaib.composenavigation.ui.navigation

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.sohaib.composenavigation.ui.navigation.route.Route
import com.sohaib.composenavigation.ui.screens.DetailScreen
import com.sohaib.composenavigation.ui.screens.HomeScreen

@Composable
fun NavGraph(
    modifier: Modifier = Modifier
) {
    val navController = rememberNavController()

    NavHost(
        navController = navController,
        startDestination = Route.HomeScreen.route,
        modifier = modifier
    ) {
        composable(route = Route.HomeScreen.route) {
            HomeScreen(
                goToDetail = {
                    navController.navigate(route = Route.DetailScreen.createRoute(it))
                }
            )
        }
        composable(
            route = Route.DetailScreen.route,
            arguments = listOf(
                navArgument(Route.DetailScreen.ARG_MESSAGE) { type = NavType.StringType }
            )
        ) { backStackEntry ->
            val message = backStackEntry.arguments?.getString(Route.DetailScreen.ARG_MESSAGE).orEmpty()
            DetailScreen(
                message = message,
                navigateBack = {
                    navController.popBackStack()
                }
            )
        }
    }
}