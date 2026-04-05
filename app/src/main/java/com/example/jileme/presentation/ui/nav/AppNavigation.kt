package com.example.jileme.presentation.ui.nav

import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.navArgument
import com.example.jileme.presentation.ui.habit.HabitCalendarScreen
import com.example.jileme.presentation.ui.profile.ProfileScreen

@Composable
fun AppNavigation(navController: NavHostController) {
    val navBackStackEntry by navController.currentBackStackEntryAsState()
    val currentRoute = navBackStackEntry?.destination?.route

    Scaffold(
        bottomBar = {
            GlassIosStyleBottomBar(
                currentRoute = currentRoute,
                onTabSelected = { route ->
                    navController.navigate(route) {
                        // 与 NavHost startDestination 一致；避免依赖 findStartDestination 扩展（部分 Navigation 版本不可用）
                        popUpTo(Screen.ModuleJie.route) {
                            saveState = true
                        }
                        launchSingleTop = true
                        restoreState = true
                    }
                }
            )
        }
    ) { innerPadding ->
        NavHost(
            navController = navController,
            startDestination = Screen.ModuleJie.route,
            modifier = Modifier
                .padding(innerPadding)
                // 避免悬浮玻璃条/阴影盖住列表底部文字
                .padding(bottom = FloatingBottomBarContentExtra)
        ) {
            composable(
                route = Screen.ModuleJie.route,
                arguments = listOf(
                    navArgument("type") {
                        type = NavType.StringType
                        defaultValue = "jie_se"
                    }
                )
            ) {
                HabitCalendarScreen(habitType = "jie_se")
            }
            composable(
                route = Screen.ModuleChou.route,
                arguments = listOf(
                    navArgument("type") {
                        type = NavType.StringType
                        defaultValue = "chou_yan"
                    }
                )
            ) {
                HabitCalendarScreen(habitType = "chou_yan")
            }
            composable(
                route = Screen.ModuleLa.route,
                arguments = listOf(
                    navArgument("type") {
                        type = NavType.StringType
                        defaultValue = "la_shi"
                    }
                )
            ) {
                HabitCalendarScreen(habitType = "la_shi")
            }

            composable(Screen.Profile.route) {
                ProfileScreen()
            }
        }
    }
}
