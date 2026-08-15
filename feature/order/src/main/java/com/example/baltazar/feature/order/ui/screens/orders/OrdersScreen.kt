package com.example.baltazar.feature.order.ui.screens.orders

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.navigation.NavController
import com.example.baltazar.core.R
import com.example.baltazar.core.core.components.CustomAppBar
import com.example.baltazar.core.core.constants.Paddings
import com.example.baltazar.core.core.enums.HomeTab
import com.example.baltazar.core.core.enums.TitleAlignment

@Composable
fun OrdersScreen(
    navController: NavController,
    onNavigateToTab: (HomeTab) -> Unit = {},
    viewModel: OrdersViewModel = androidx.lifecycle.viewmodel.compose.viewModel()
) {
    val state by viewModel.uiState.collectAsState()

    Scaffold(
        topBar = {
            CustomAppBar(
                title = stringResource(R.string.orders_title),
                alignment = TitleAlignment.CENTER
            )
        }
    ) { innerPadding ->
        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
                .padding(Paddings.Medium),
            contentAlignment = Alignment.Center
        ) {
            Text(
                text = stringResource(R.string.orders_empty_message),
                style = MaterialTheme.typography.bodyLarge,
                color = MaterialTheme.colorScheme.onSurfaceVariant
            )
        }
    }
}
