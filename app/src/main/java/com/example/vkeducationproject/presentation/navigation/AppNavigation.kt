package com.example.vkeducationproject.presentation.navigation

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.example.vkeducationproject.data.repository.AppRepositoryImpl
import com.example.vkeducationproject.presentation.apppage.AppDetailsScreen
import com.example.vkeducationproject.presentation.appsmarket.MainDisplay
import com.example.vkeducationproject.presentation.viewmodels.AppViewModel
import com.example.vkeducationproject.presentation.viewmodels.AppViewModelFactory

class AppNavigation {
    companion object Ways {
        const val HOME_PAGE = "home"
        const val APP_PAGE = "app_page/{appId}"  // Маршрут с параметром
        const val PARAM_APP_ID = "appId"
    }
    @Composable
    fun NavigationGraph(
        modifier: Modifier = Modifier.Companion// Создаем одну ViewModel для всего графа
    ) {
        val navController = rememberNavController()
        val viewModel: AppViewModel = viewModel(
            factory = AppViewModelFactory(AppRepositoryImpl())
        )

        NavHost(
            navController = navController,
            startDestination = HOME_PAGE,
            modifier = modifier
        ) {
            composable(route = HOME_PAGE) {
                MainDisplay(
                    navController = navController,
                    viewModel = viewModel
                )
            }

            composable(
                route = APP_PAGE,
                arguments = listOf(
                    navArgument(PARAM_APP_ID) {
                        type = NavType.Companion.StringType
                        defaultValue = ""
                    }
                )
            ) { backStackEntry ->
                // Получаем ID из аргументов
                val appId = backStackEntry.arguments?.getString(PARAM_APP_ID) ?: ""

                AppDetailsScreen(
                    id = appId,
                    viewModel = viewModel,
                    onBack = { navController.navigate(HOME_PAGE) }
                )
            }
        }
    }
}