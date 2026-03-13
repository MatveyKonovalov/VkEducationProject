package com.example.vkeducationproject.presentation

import android.util.Log
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.example.vkeducationproject.MainDisplay
import com.example.vkeducationproject.presentation.models.App
import com.example.vkeducationproject.presentation.apppage.AppDetailsScreen
import com.example.vkeducationproject.presentation.viewmodels.AppViewModel
import kotlin.collections.get



class AppNavigation {
    companion object Ways {
        const val HOME_PAGE = "home"
        const val APP_PAGE = "app_page/{appId}"  // Маршрут с параметром
        const val PARAM_APP_ID = "appId"
    }

    @Composable
    fun NavigationGraph(
        modifier: Modifier = Modifier,
        viewModel: AppViewModel = viewModel()  // Создаем одну ViewModel для всего графа
    ) {
        val navController = rememberNavController()

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
                        type = NavType.StringType
                        defaultValue = ""
                    }
                )
            ) { backStackEntry ->
                // Получаем ID из аргументов
                val appId = backStackEntry.arguments?.getString(PARAM_APP_ID) ?: ""


                // Получаем данные приложения по ID
                val appData = viewModel.getAppById(appId)
                Log.d("app", appId)

                AppDetailsScreen(
                    app = appData,
                    onBack = { navController.navigate(HOME_PAGE)}
                )
            }
        }
    }
}