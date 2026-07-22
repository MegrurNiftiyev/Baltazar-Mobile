// core/navigation/NavGraph.kt
package com.example.baltazar.core.navigation

import androidx.compose.animation.core.tween
import androidx.compose.animation.slideInHorizontally
import androidx.compose.animation.slideOutHorizontally
import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.baltazar.ui.screens.home.HomeScreen
import com.example.baltazar.ui.screens.onboarding.OnboardingScreen


@Composable
fun AppNavGraph(
    navController: NavHostController = rememberNavController()
) {
    NavHost(
        navController = navController, startDestination = Onboarding,

        enterTransition = {
            slideInHorizontally(
                initialOffsetX = { fullWidth -> fullWidth },
                animationSpec = tween(300)
            )
        },
        exitTransition = {
            slideOutHorizontally(
                targetOffsetX = { fullWidth -> -fullWidth },
                animationSpec = tween(300)
            )
        },
        popEnterTransition = {
            slideInHorizontally(
                initialOffsetX = { fullWidth -> -fullWidth },
                animationSpec = tween(300)
            )
        },
        popExitTransition = {
            slideOutHorizontally(
                targetOffsetX = { fullWidth -> fullWidth },
                animationSpec = tween(300)
            )
        }
    ) {
        composable<Onboarding> { OnboardingScreen(navController) }
        composable<Home> { HomeScreen(navController) }

//        composable<NoteDetail> { backStackEntry ->
//            val args = backStackEntry.toRoute<NoteDetail>()
//            NoteDetailScreen(args.id, args.title, args.subtitle, args.isSynced, navController)
//        }
    }
}