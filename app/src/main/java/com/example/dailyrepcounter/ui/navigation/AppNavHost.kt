// AppNavHost.kt
package com.example.dailyrepcounter.ui.navigation

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import com.example.dailyrepcounter.ui.screens.dashboard.DashboardScreen
import com.example.dailyrepcounter.ui.screens.exercise.AddExerciseScreen
import com.example.dailyrepcounter.ui.screens.exercise.ExerciseDetailScreen
import com.example.dailyrepcounter.ui.screens.exercise.ExercisesScreen
import com.example.dailyrepcounter.ui.screens.session.AddSessionScreen

@Composable
fun AppNavHost(
    navController: NavHostController,
    modifier: Modifier = Modifier
) {
    NavHost(
        navController = navController,
        startDestination = Screen.Dashboard.route,
        modifier = modifier
    ) {
        composable(Screen.Dashboard.route) {
            DashboardScreen(
                onNavigateToExercises = { navController.navigate(Screen.Exercises.route) }
            )
        }

        composable(Screen.Exercises.route) {
            ExercisesScreen(
                onAddExerciseClick = { navController.navigate(Screen.AddExercise.route) },
                onExerciseClick = { exerciseId ->
                    navController.navigate(Screen.ExerciseDetail.createRoute(exerciseId))
                }
            )
        }

        composable(Screen.AddExercise.route) {
            AddExerciseScreen(
                onExerciseAdded = { navController.popBackStack() }
            )
        }

        composable(
            route = Screen.ExerciseDetail.route,
            arguments = listOf(navArgument("exerciseId") { type = NavType.LongType })
        ) { entry ->
            val exerciseId = entry.arguments?.getLong("exerciseId") ?: 0L
            ExerciseDetailScreen(
                exerciseId = exerciseId,
                onAddSessionClick = {
                    navController.navigate(Screen.AddSession.createRoute(exerciseId))
                },
                onBackClick = { navController.popBackStack() }
            )
        }

        composable(
            route = Screen.AddSession.route,
            arguments = listOf(navArgument("exerciseId") { type = NavType.LongType })
        ) { entry ->
            val exerciseId = entry.arguments?.getLong("exerciseId") ?: 0L
            AddSessionScreen(
                exerciseId = exerciseId,
                onSessionAdded = { navController.popBackStack() }
            )
        }
    }
}

sealed class Screen(val route: String) {
    object Dashboard : Screen("dashboard")
    object Exercises : Screen("exercises")
    object AddExercise : Screen("add-exercise")
    object ExerciseDetail : Screen("exercise/{exerciseId}") {
        fun createRoute(exerciseId: Long) = "exercise/$exerciseId"
    }
    object AddSession : Screen("exercise/{exerciseId}/add-session") {
        fun createRoute(exerciseId: Long) = "exercise/$exerciseId/add-session"
    }
}