package com.example.baltazar.feature.auth.ui.screens.auth_selection

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextDecoration
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.example.baltazar.core.components.RoundedButton
import com.example.baltazar.core.constants.Paddings
import com.example.baltazar.core.constants.Spaces
import com.example.baltazar.core.navigation.Home
import com.example.baltazar.core.navigation.Login
import com.example.baltazar.core.navigation.Register
import com.example.baltazar.feature.auth.R

@Composable
fun AuthSelectionScreen(
    navController: NavController,
    modifier: Modifier = Modifier,
    viewModel: AuthSelectionViewModel = hiltViewModel()
) {
    val state by viewModel.state.collectAsState()

    Column(
        modifier = modifier
            .fillMaxSize()
            .padding(Paddings.Medium),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Text(
            text = stringResource(R.string.auth_selection_title),
            style = MaterialTheme.typography.headlineMedium,
            color = MaterialTheme.colorScheme.onSurface,
            textAlign = TextAlign.Center
        )

        Spacer(modifier = Modifier.height(Spaces.Small))

        Text(
            text = stringResource(R.string.auth_selection_subtitle),
            style = MaterialTheme.typography.bodyLarge,
            color = MaterialTheme.colorScheme.onSurfaceVariant,
            textAlign = TextAlign.Center
        )

        Spacer(modifier = Modifier.height(Spaces.Giant))

        RoundedButton(
            text = stringResource(R.string.auth_selection_login),
            onClick = { navController.navigate(Login) },
            modifier = Modifier.fillMaxWidth()
        )

        Spacer(modifier = Modifier.height(Spaces.Medium))

        RoundedButton(
            text = stringResource(R.string.auth_selection_register),
            onClick = { navController.navigate(Register) },
            modifier = Modifier.fillMaxWidth()
        )

        Spacer(modifier = Modifier.height(Spaces.Large))

        Text(
            text = stringResource(R.string.auth_selection_guest),
            style = MaterialTheme.typography.bodyMedium,
            color = MaterialTheme.colorScheme.primary,
            textDecoration = TextDecoration.Underline,
            modifier = Modifier.clickable {
                viewModel.continueAsGuest()
                navController.navigate(Home) {
                    popUpTo(0) { inclusive = true }
                }
            }
        )
    }
}
