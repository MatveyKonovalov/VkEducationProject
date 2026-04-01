package com.example.vkeducationproject.presentation.navigation

import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Modifier
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.example.vkeducationproject.presentation.apppage.AppDetailsScreen
import com.example.vkeducationproject.presentation.appsmarket.MainDisplay
import com.example.vkeducationproject.presentation.viewmodels.AppDetailsViewModel
import com.example.vkeducationproject.presentation.viewmodels.AppViewModel

object AppNavigation {
    const val HOME_PAGE = "home"
    const val APP_PAGE = "app_page/{appId}"
    const val PARAM_APP_ID = "appId"
}

@Composable
fun NavigationGraph(
    modifier: Modifier = Modifier
) {
    val navController = rememberNavController()

    val sharedViewModel: AppViewModel = viewModel()


    NavHost(
        navController = navController,
        startDestination = AppNavigation.HOME_PAGE,
        modifier = modifier
    ) {
        composable(route = AppNavigation.HOME_PAGE) {
            MainDisplay(
                navController = navController,
                viewModel = sharedViewModel
            )
        }

        composable(
            route = AppNavigation.APP_PAGE,
            arguments = listOf(
                navArgument(AppNavigation.PARAM_APP_ID) {
                    type = NavType.StringType
                    defaultValue = ""
                }
            )
        ) { backStackEntry ->
            val appId = backStackEntry.arguments?.getString(AppNavigation.PARAM_APP_ID) ?: ""
            val appDetailsViewModel: AppDetailsViewModel = hiltViewModel()

            LaunchedEffect(appId) {
                println(appId)
                appDetailsViewModel.loadAppDetails(appId)
                println(appDetailsViewModel.currentApp)
            }
            AppDetailsScreen(
                viewModel = appDetailsViewModel,
                onBack = { navController.popBackStack() }
            )

        }
    }
}