package com.example.baltazar.core.core.managers

import androidx.navigation.NavController
import com.example.baltazar.core.core.navigation.Login
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class AuthGateManager @Inject constructor(
    private val sessionManager: SessionManager
) {
    fun isAuthenticated(): Boolean {
        return !sessionManager.user.value.isGuest
    }

    fun isGuest(): Boolean {
        return sessionManager.user.value.isGuest
    }

    fun requireAuth(
        onLoginRequired: () -> Unit,
        onAuthenticated: () -> Unit
    ) {
        if (isAuthenticated()) {
            onAuthenticated()
        } else {
            onLoginRequired()
        }
    }
}

/**
 * UI extension helper to require authentication before executing an action.
 * Centralizes the Login redirection in the UI layer while keeping AuthGateManager free of NavController dependency.
 */
inline fun AuthGateManager.requireAuth(
    navController: NavController,
    crossinline onAuthenticated: () -> Unit
) {
    requireAuth(
        onLoginRequired = { navController.navigate(Login(isPopStack = true)) },
        onAuthenticated = { onAuthenticated() }
    )
}
