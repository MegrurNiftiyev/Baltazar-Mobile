package com.example.baltazar.feature.hotel.core.navigation

import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.compose.composable
import androidx.navigation.toRoute
import com.example.baltazar.core.core.navigation.HotelDetail
import com.example.baltazar.core.core.navigation.HotelList
import com.example.baltazar.feature.hotel.ui.screens.hotel_detail.HotelDetailScreen
import com.example.baltazar.feature.hotel.ui.screens.hotels.HotelsScreen

fun NavGraphBuilder.hotelNavGraph(navController: NavHostController) {
    composable<HotelList> { HotelsScreen(navController) }
    composable<HotelDetail> { backStackEntry ->
        val args = backStackEntry.toRoute<HotelDetail>()
        HotelDetailScreen(navController)
    }
}

