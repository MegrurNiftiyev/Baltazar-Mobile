package com.example.baltazar.core.navigation

import androidx.compose.animation.core.tween
import androidx.compose.animation.slideInHorizontally
import androidx.compose.animation.slideOutHorizontally
import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.baltazar.feature.auth.core.navigation.authNavGraph
import com.example.baltazar.feature.explore.core.navigation.exploreNavGraph
import com.example.baltazar.feature.food.core.navigation.foodNavGraph
import com.example.baltazar.feature.hotel.core.navigation.hotelNavGraph
import com.example.baltazar.feature.order.core.navigation.orderNavGraph
import com.example.baltazar.feature.profile.core.navigation.profileNavGraph
import com.example.baltazar.feature.rentacar.core.navigation.rentACarNavGraph
import com.example.baltazar.feature.taxi.core.navigation.taxiNavGraph
import com.example.baltazar.feature.travel.core.navigation.travelNavGraph
import com.example.baltazar.ui.screens.home.HomeScreen
import com.example.baltazar.ui.screens.splash.SplashScreen

@Composable
fun AppNavGraph(
    startDestination: Any = Splash,
    navController: NavHostController = rememberNavController()
) {
    NavHost(
        navController = navController,
        startDestination = startDestination,
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
        composable<Splash> { SplashScreen(navController) }
        composable<Home> { HomeScreen(navController) }

        // Modular NavGraphs from Feature Modules
        authNavGraph(navController)
        exploreNavGraph(navController)
        foodNavGraph(navController)
        hotelNavGraph(navController)
        travelNavGraph(navController)
        rentACarNavGraph(navController)
        taxiNavGraph(navController)
        profileNavGraph(navController)
        orderNavGraph(navController)
    }
}
