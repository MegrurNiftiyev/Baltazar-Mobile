package com.example.baltazar.feature.order.ui.screens.orders

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.navigation.NavController
import com.example.baltazar.core.R
import com.example.baltazar.core.core.components.CustomAppBar
import com.example.baltazar.core.core.components.EmptyStateView
import com.example.baltazar.core.core.enums.HomeTab
import com.example.baltazar.core.core.enums.TitleAlignment
import compose.icons.TablerIcons
import compose.icons.tablericons.Receipt

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
        },
        containerColor = MaterialTheme.colorScheme.background
    ) { innerPadding ->
        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
        ) {
            when {
                state.isLoading -> {
                    androidx.compose.foundation.lazy.LazyColumn(
                        contentPadding = androidx.compose.foundation.layout.PaddingValues(com.example.baltazar.core.core.constants.Paddings.Medium),
                        verticalArrangement = androidx.compose.foundation.layout.Arrangement.spacedBy(com.example.baltazar.core.core.constants.Spaces.Medium),
                        modifier = Modifier.fillMaxSize()
                    ) {
                        items(5) {
                            com.example.baltazar.core.core.components.StandardItemCard(
                                onClick = {},
                                isLoading = true,
                                cardViewMode = com.example.baltazar.core.core.enums.CardViewMode.LIST
                            )
                        }
                    }
                }
                state.orders.isEmpty() -> {
                    Box(
                        modifier = Modifier.fillMaxSize(),
                        contentAlignment = Alignment.Center
                    ) {
                        EmptyStateView(
                            icon = TablerIcons.Receipt,
                            title = stringResource(R.string.orders_empty_title),
                            subtitle = stringResource(R.string.orders_empty_subtitle),
                            actionButtonText = stringResource(R.string.explore_services),
                            onActionClick = { onNavigateToTab(HomeTab.Explore) }
                        )
                    }
                }
            }
        }
    }
}
