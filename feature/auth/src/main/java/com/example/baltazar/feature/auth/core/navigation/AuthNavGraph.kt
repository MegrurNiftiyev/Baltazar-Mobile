package com.example.baltazar.feature.auth.core.navigation

import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.compose.composable
import com.example.baltazar.core.navigation.Login
import com.example.baltazar.core.navigation.Onboarding
import com.example.baltazar.core.navigation.Register
import com.example.baltazar.feature.auth.ui.screens.login.LoginScreen
import com.example.baltazar.feature.auth.ui.screens.onboarding.OnboardingScreen
import com.example.baltazar.feature.auth.ui.screens.register.RegisterScreen

fun NavGraphBuilder.authNavGraph(navController: NavHostController) {
    composable<Onboarding> { OnboardingScreen(navController) }
    composable<Login> { LoginScreen(navController) }
    composable<Register> { RegisterScreen(navController) }
}
