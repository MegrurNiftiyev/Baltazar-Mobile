package com.example.baltazar.feature.profile.core.navigation

import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.compose.composable
import com.example.baltazar.core.core.navigation.Profile
import com.example.baltazar.core.core.navigation.Wishlist
import com.example.baltazar.feature.profile.ui.screens.profile.ProfileScreen
import com.example.baltazar.feature.profile.ui.screens.wishlist.WishlistScreen

fun NavGraphBuilder.profileNavGraph(navController: NavHostController) {
    composable<Profile> { ProfileScreen(navController) }
    composable<Wishlist> { WishlistScreen(navController) }
}

