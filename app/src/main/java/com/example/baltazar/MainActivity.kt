package com.example.baltazar

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarHostState
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import com.example.baltazar.core.core.components.AppSnackbarHost
import com.example.baltazar.core.core.navigation.Home
import com.example.baltazar.core.core.navigation.Login
import com.example.baltazar.core.core.navigation.Onboarding
import com.example.baltazar.core.core.theme.BaltazarAppTheme
import com.example.baltazar.core.core.utils.AppSnackbar
import com.example.baltazar.core.domain.repository.ISettingsRepository
import com.example.baltazar.core.navigation.AppNavGraph
import com.example.baltazar.ui.screens.splash.SplashViewModel
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class MainActivity : ComponentActivity() {

    @Inject
    lateinit var settingsRepository: ISettingsRepository

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
            val isDarkMode by settingsRepository.isDarkMode.collectAsState(initial = false)
            val snackbarHostState = remember { SnackbarHostState() }

            LaunchedEffect(Unit) {
                AppSnackbar.bind(snackbarHostState)
            }

            BaltazarAppTheme(darkTheme = isDarkMode) {
                if (!state.isLoading) {
                    val startDest: Any = when {
                        !state.isOnboarded -> Onboarding
                        !state.isLoginFinished -> Login
                        else -> Home()
                    }

                    Scaffold(
                        snackbarHost = { AppSnackbarHost(snackbarHostState) }
                    ) { innerPadding ->
                        AppNavGraph(
                            startDestination = startDest,
                            modifier = Modifier.padding(innerPadding)
                        )
                    }
                }
            }
        }
    }
}
