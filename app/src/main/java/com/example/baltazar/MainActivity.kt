package com.example.baltazar

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import com.example.baltazar.core.navigation.AppNavGraph
import com.example.baltazar.core.navigation.Home
import com.example.baltazar.core.navigation.Login
import com.example.baltazar.core.navigation.Onboarding
import com.example.baltazar.core.theme.BaltazarAppTheme
import com.example.baltazar.ui.screens.splash.SplashViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {

    val splashViewModel: SplashViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        val splashScreen = installSplashScreen()

        super.onCreate(savedInstanceState)
        splashScreen.setKeepOnScreenCondition {
            splashViewModel.state.value.isLoading
        }

        enableEdgeToEdge()
        setContent {
            val state by splashViewModel.state.collectAsState()

            BaltazarAppTheme {
                if (!state.isLoading) {
                    val startDest = when {
                        !state.isOnboarded -> Onboarding
                        !state.isLoginFinished -> Login
                        else -> Home
                    }

                    AppNavGraph(startDestination = startDest)
                }
            }
        }
    }
}