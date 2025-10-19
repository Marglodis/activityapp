package com.mtovar.activityapp.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.mtovar.activityapp.ui.screens.ActivityFormScreen
import com.mtovar.activityapp.ui.screens.ActivityListScreen
import com.mtovar.activityapp.viewmodel.ActivityViewModel

sealed class Screen(val route: String) {
    object ActivityList : Screen("activity_list")
    object ActivityForm : Screen("activity_form")
}

@Composable
fun AppNavigation(viewModel: ActivityViewModel) {
    val navController = rememberNavController()

    NavHost(
        navController = navController,
        startDestination = Screen.ActivityList.route
    ) {
        composable(Screen.ActivityList.route) {
            ActivityListScreen(
                viewModel = viewModel,
                onNavigateToForm = {
                    navController.navigate(Screen.ActivityForm.route)
                }
            )
        }

        composable(Screen.ActivityForm.route){
            ActivityFormScreen(
                viewModel = viewModel,
                onNavigateBack = {
                    navController.popBackStack()
                }
            )
        }
    }
}