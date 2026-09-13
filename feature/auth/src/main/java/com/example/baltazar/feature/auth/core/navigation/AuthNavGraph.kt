package com.example.baltazar.feature.auth.core.navigation

import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.compose.composable
import com.example.baltazar.core.core.navigation.AuthSelection
import com.example.baltazar.core.core.navigation.Login
import com.example.baltazar.core.core.navigation.Onboarding
import com.example.baltazar.core.core.navigation.Register
import com.example.baltazar.feature.auth.ui.screens.auth_selection.AuthSelectionScreen
import com.example.baltazar.feature.auth.ui.screens.login.LoginScreen
import com.example.baltazar.feature.auth.ui.screens.onboarding.OnboardingScreen
import com.example.baltazar.feature.auth.ui.screens.register.RegisterScreen

import androidx.navigation.toRoute

fun NavGraphBuilder.authNavGraph(navController: NavHostController) {
    composable<Onboarding> { OnboardingScreen(navController) }
    composable<AuthSelection> { AuthSelectionScreen(navController) }
    composable<Login> { backStackEntry ->
        val loginRoute = backStackEntry.toRoute<Login>()
        LoginScreen(navController = navController, isBackPrevious = loginRoute.isBackPrevious)
    }
    composable<Register> { backStackEntry ->
        val registerRoute = backStackEntry.toRoute<Register>()
        RegisterScreen(navController = navController, isBackPrevious = registerRoute.isBackPrevious)
    }
}

