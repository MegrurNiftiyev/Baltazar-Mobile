package com.example.baltazar.feature.rentacar.core.navigation

import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.compose.composable
import androidx.navigation.toRoute
import com.example.baltazar.core.core.navigation.RentACarDetail
import com.example.baltazar.core.core.navigation.RentACarList
import com.example.baltazar.feature.rentacar.ui.screens.rentacar_company.RentACarCompanyScreen
import com.example.baltazar.feature.rentacar.ui.screens.rentacar_detail.RentACarDetailScreen
import com.example.baltazar.feature.rentacar.ui.screens.rentacars.RentACarsScreen

fun NavGraphBuilder.rentACarNavGraph(navController: NavHostController) {
    composable<RentACarList> { RentACarsScreen(navController) }
    composable<RentACarDetail> { backStackEntry ->
        val args = backStackEntry.toRoute<RentACarDetail>()
        RentACarDetailScreen(navController)
    }
}

