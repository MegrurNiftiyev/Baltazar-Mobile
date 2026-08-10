package com.example.baltazar.feature.auth.ui.screens.auth_selection

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.widthIn
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.example.baltazar.core.components.CustomTextButton
import com.example.baltazar.core.components.RoundedButton
import com.example.baltazar.core.constants.Paddings
import com.example.baltazar.core.constants.Spaces
import com.example.baltazar.core.navigation.Home
import com.example.baltazar.core.navigation.Login
import com.example.baltazar.feature.auth.R
import  com.example.baltazar.core.R as coreR

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
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Spacer(modifier = Modifier.weight(1f))
        Image(
            painter = painterResource(id = coreR.drawable.logo),
            contentDescription = null,
            contentScale = ContentScale.Fit,
            modifier = Modifier
                .fillMaxWidth(0.6f)
                .widthIn(max = 320.dp)
                .aspectRatio(1f)
        )
        Text(
            text = stringResource(R.string.auth_selection_title),
            style = MaterialTheme.typography.headlineMedium,
            color = MaterialTheme.colorScheme.onSurface,
            textAlign = TextAlign.Center
        )

        Spacer(modifier = Modifier.height(Spaces.Medium))

        Text(
            text = stringResource(R.string.auth_selection_subtitle),
            style = MaterialTheme.typography.bodyLarge,
            color = MaterialTheme.colorScheme.onSurfaceVariant,
            textAlign = TextAlign.Center
        )

        Spacer(modifier = Modifier.height(Spaces.Giant))

        RoundedButton(
            text = stringResource(R.string.auth_selection_login),
            textStyle = MaterialTheme.typography.labelLarge,
            onClick = { navController.navigate(Login) },
            modifier = Modifier.fillMaxWidth()
        )



        Spacer(modifier = Modifier.height(Spaces.Huge))
        CustomTextButton(
            text = stringResource(R.string.auth_selection_guest),
            textStyle = MaterialTheme.typography.bodyMedium.copy(MaterialTheme.colorScheme.primary),
            onClick = {
                navController.navigate(Home) {
                    popUpTo(0) { inclusive = true }
                }
            }
        )

    }
}
