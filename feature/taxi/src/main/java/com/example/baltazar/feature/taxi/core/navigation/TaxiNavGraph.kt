package com.example.baltazar.feature.taxi.core.navigation

import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.compose.composable
import com.example.baltazar.feature.taxi.ui.screens.taxi.TaxiScreen
import kotlinx.serialization.Serializable

@Serializable
object Taxi

fun NavGraphBuilder.taxiNavGraph(navController: NavHostController) {
    composable<Taxi> { TaxiScreen(navController) }
}
