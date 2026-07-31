package com.example.baltazar.core.navigation

import androidx.compose.animation.core.tween
import androidx.compose.animation.slideInHorizontally
import androidx.compose.animation.slideOutHorizontally
import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.rememberNavController

@Composable
fun AppNavGraph(
    navController: NavHostController = rememberNavController()
) {
    NavHost(
        navController = navController,
        startDestination = Splash,
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
//        composable<Splash> { SplashScreen(navController) }
//        composable<Onboarding> { OnboardingScreen(navController) }
//
//        composable<Explore> { ExploreScreen(navController) }
//        composable<Wishlist> { WishlistScreen(navController) }
//        composable<Profile> { ProfileScreen(navController) }
//
//        composable<RentACarList> { RentACarListScreen(navController) }
//        composable<RentACarDetail> { backStackEntry ->
//            val args = backStackEntry.toRoute<RentACaDetail>()
//            RentACarDetailScreen(args.id, navController)
//        }
//
//        composable<HotelList> { HotelListScreen(navController) }
//        composable<HotelDetail> { backStackEntry ->
//            val args = backStackEntry.toRoute<HotelDetail>()
//            HotelDetailScreen(args.id, navController)
//        }
//
//        composable<TravelList> { TravelListScreen(navController) }
//        composable<TravelDetail> { backStackEntry ->
//            val args = backStackEntry.toRoute<TravelDetail>()
//            TravelDetailScreen(args.id, navController)
//        }
//
//        composable<FoodCompanyList> { FoodCompanyListScreen(navController) }
//        composable<FoodCompanyDetail> { backStackEntry ->
//            val args = backStackEntry.toRoute<FoodCompanyDetail>()
//            FoodCompanyDetailScreen(args.id, navController)
//        }
//
//        composable<OrderFlow> { backStackEntry ->
//            val args = backStackEntry.toRoute<OrderFlow>()
//            OrderFlowScreen(args.serviceType, args.serviceId, navController)
//        }
    }
}