package com.example.baltazar.feature.explore.core.navigation

import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.compose.composable
import com.example.baltazar.core.core.navigation.Explore
import com.example.baltazar.feature.explore.ui.screens.explore.ExploreScreen

fun NavGraphBuilder.exploreNavGraph(navController: NavHostController) {
    composable<Explore> { ExploreScreen(navController) }
}

