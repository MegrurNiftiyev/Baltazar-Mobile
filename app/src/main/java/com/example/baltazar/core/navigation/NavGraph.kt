package com.example.baltazar.core.navigation

import androidx.compose.animation.core.tween
import androidx.compose.animation.slideInHorizontally
import androidx.compose.animation.slideOutHorizontally
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.toRoute
import com.example.baltazar.core.core.navigation.Home
import com.example.baltazar.core.core.navigation.Splash
import com.example.baltazar.feature.auth.core.navigation.authNavGraph
import com.example.baltazar.feature.explore.core.navigation.exploreNavGraph
import com.example.baltazar.feature.food.core.navigation.foodNavGraph
import com.example.baltazar.feature.hotel.core.navigation.hotelNavGraph
import com.example.baltazar.feature.order.core.navigation.orderNavGraph
import com.example.baltazar.feature.profile.core.navigation.profileNavGraph
import com.example.baltazar.feature.rentacar.core.navigation.rentACarNavGraph
import com.example.baltazar.feature.travel.core.navigation.travelNavGraph
import com.example.baltazar.ui.screens.home.HomeScreen

@Composable
fun AppNavGraph(
    startDestination: Any = Splash,
    navController: NavHostController = rememberNavController(),
    modifier: Modifier = Modifier
) {
    NavHost(
        navController = navController,
        startDestination = startDestination,
        modifier = modifier,
        enterTransition = {
            slideInHorizontally(
                initialOffsetX = { it },
                animationSpec = tween(300)
            )
        },
        exitTransition = {
            slideOutHorizontally(
                targetOffsetX = { -it },
                animationSpec = tween(300)
            )
        },
        popEnterTransition = {
            slideInHorizontally(
                initialOffsetX = { -it },
                animationSpec = tween(300)
            )
        },
        popExitTransition = {
            slideOutHorizontally(
                targetOffsetX = { it },
                animationSpec = tween(300)
            )
        }
    ) {
        // App Module Screens
        composable<Home> { backStackEntry ->
            val home = backStackEntry.toRoute<Home>()
            HomeScreen(navController, home.initialTab)
        }

        // Modular NavGraphs from Feature Modules
        authNavGraph(navController)
        exploreNavGraph(navController)
        foodNavGraph(navController)
        hotelNavGraph(navController)
        travelNavGraph(navController)
        rentACarNavGraph(navController)
        profileNavGraph(navController)
        orderNavGraph(navController)
    }
}
