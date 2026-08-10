package com.example.baltazar.feature.travel.core.navigation

import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.compose.composable
import androidx.navigation.toRoute
import com.example.baltazar.core.core.navigation.TravelDetail
import com.example.baltazar.core.core.navigation.TravelList
import com.example.baltazar.feature.travel.ui.screens.travel_company.TravelCompanyScreen
import com.example.baltazar.feature.travel.ui.screens.travel_detail.TravelDetailScreen
import com.example.baltazar.feature.travel.ui.screens.travels.TravelsScreen

fun NavGraphBuilder.travelNavGraph(navController: NavHostController) {
    composable<TravelList> { TravelsScreen(navController) }
    composable<TravelDetail> { backStackEntry ->
        val args = backStackEntry.toRoute<TravelDetail>()
        TravelDetailScreen(navController)
    }
}

