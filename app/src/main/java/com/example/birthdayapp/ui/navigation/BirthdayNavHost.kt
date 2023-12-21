package com.example.birthdayapp.ui.navigation

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import com.example.birthdayapp.ui.screens.BirthdayDetailsDestination
import com.example.birthdayapp.ui.screens.BirthdayDetailsScreen
import com.example.birthdayapp.ui.screens.BirthdayDisplayDestination
import com.example.birthdayapp.ui.screens.BirthdayDisplayScreen
import com.example.birthdayapp.ui.screens.BirthdayEditDestination
import com.example.birthdayapp.ui.screens.BirthdayEditScreen
import com.example.birthdayapp.ui.screens.BirthdayEntryDestination
import com.example.birthdayapp.ui.screens.BirthdayEntryScreen
import com.example.birthdayapp.ui.screens.HomeDestination
import com.example.birthdayapp.ui.screens.HomeScreen

@Composable
fun BirthdayNavHost(
    navController: NavHostController,
    modifier: Modifier = Modifier
) {
    NavHost(
        navController = navController,
        startDestination = HomeDestination.route,
        modifier = modifier
    ) {

        // Home Route
        composable(route = HomeDestination.route) {
            HomeScreen(
                navigateToBirthdayDisplay = { navController.navigate(BirthdayDisplayDestination.route)}
            )
        }

        // Display Route
        composable(route = BirthdayDisplayDestination.route) {
            BirthdayDisplayScreen(
                navigateBack = { navController.popBackStack() },
                navigateToEntry = { navController.navigate(BirthdayEntryDestination.route) },
                navigateToDetails = {
                    navController.navigate("${BirthdayDetailsDestination.route}/${it}")
                }
            )
        }

        // Entry Route
        composable(route = BirthdayEntryDestination.route) {
            BirthdayEntryScreen(
                navigateBack = { navController.popBackStack() },
                onNavigateUp = { navController.navigateUp() }
            )
        }

        // Details Route
        composable(
            route = BirthdayDetailsDestination.routeWithArgs,
            arguments = listOf(navArgument(BirthdayDetailsDestination.personIdArg) {
                type = NavType.IntType
            })
        ) {
            BirthdayDetailsScreen(
                navigateUp = { navController.popBackStack() },
                navigateToEdit = {
                    navController.navigate("${BirthdayEditDestination.route}/${it}")
                }
            )
        }

        // Edit Route
        composable(
            route = BirthdayEditDestination.routeWithArgs,
            arguments = listOf(navArgument(BirthdayEditDestination.personIdArg) {
                type = NavType.IntType
            })
        ) {
            BirthdayEditScreen(
                navigateUp = { navController.navigateUp() },
                navigateBack = { navController.popBackStack() }
            )
        }
    }
}