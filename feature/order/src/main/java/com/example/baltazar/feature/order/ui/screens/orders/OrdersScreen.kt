package com.example.baltazar.feature.order.ui.screens.orders

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ReceiptLong
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import com.example.baltazar.core.R
import com.example.baltazar.core.core.components.CustomAppBar
import com.example.baltazar.core.core.components.EmptyStateView
import com.example.baltazar.core.core.constants.BorderRadiuses
import com.example.baltazar.core.core.constants.IconSizes
import com.example.baltazar.core.core.constants.Paddings
import com.example.baltazar.core.core.constants.Spaces
import com.example.baltazar.core.core.enums.HomeTab
import com.example.baltazar.core.core.enums.TitleAlignment
import com.example.baltazar.core.core.navigation.Login
import compose.icons.TablerIcons
import compose.icons.tablericons.Receipt

@Composable
fun OrdersScreen(
    navController: NavHostController,
    onOrderClick: (String) -> Unit = {},
    onNavigateToTab: (HomeTab) -> Unit = {},
    viewModel: OrdersViewModel = hiltViewModel()
) {
    val state by viewModel.state.collectAsState()
    val isGuest = state.user.isGuest

    Scaffold(
        topBar = {
            CustomAppBar(
                title = stringResource(id = R.string.orders_title),
                alignment = TitleAlignment.CENTER,
                onBackClick = { navController.popBackStack() }
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
                state.isUserLoading || state.isLoading -> {
                    CircularProgressIndicator(
                        modifier = Modifier.align(Alignment.Center),
                        color = MaterialTheme.colorScheme.primary
                    )
                }

                isGuest -> {
                    Box(
                        modifier = Modifier
                            .fillMaxSize()
                            .verticalScroll(rememberScrollState()),
                        contentAlignment = Alignment.Center
                    ) {
                        EmptyStateView(
                            icon = TablerIcons.Receipt,
                            title = stringResource(R.string.orders_guest_title),
                            subtitle = stringResource(R.string.orders_guest_subtitle),
                            actionButtonText = stringResource(R.string.login),
                            onActionClick = { navController.navigate(Login(isPopStack = true)) }
                        )
                    }
                }

                state.orders.isEmpty() -> {
                    Box(
                        modifier = Modifier
                            .fillMaxSize()
                            .verticalScroll(rememberScrollState()),
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

                else -> {
                    LazyColumn(
                        modifier = Modifier
                            .fillMaxSize()
                            .padding(horizontal = Paddings.Medium)
                    ) {
                        items(state.orders) { order ->
                            Row(
                                verticalAlignment = Alignment.CenterVertically,
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .padding(vertical = Paddings.ExtraSmall)
                                    .clip(RoundedCornerShape(BorderRadiuses.Medium))
                                    .background(MaterialTheme.colorScheme.surfaceContainerLow)
                                    .clickable { onOrderClick(order.id) }
                                    .padding(Paddings.Medium)
                            ) {
                                Box(
                                    modifier = Modifier
                                        .size(48.dp)
                                        .clip(RoundedCornerShape(BorderRadiuses.Small))
                                        .background(MaterialTheme.colorScheme.primary.copy(alpha = 0.1f)),
                                    contentAlignment = Alignment.Center
                                ) {
                                    Icon(
                                        imageVector = Icons.AutoMirrored.Filled.ReceiptLong,
                                        contentDescription = null,
                                        tint = MaterialTheme.colorScheme.primary,
                                        modifier = Modifier.size(IconSizes.Medium)
                                    )
                                }
                                Spacer(modifier = Modifier.width(Spaces.Medium))
                                Column(modifier = Modifier.weight(1f)) {
                                    Text(
                                        text = order.serviceItemSnapshot?.title ?: "Order #${order.id.takeLast(6)}",
                                        style = MaterialTheme.typography.titleMedium,
                                        color = MaterialTheme.colorScheme.onSurface
                                    )
                                    Text(
                                        text = order.status.name,
                                        style = MaterialTheme.typography.bodySmall,
                                        color = MaterialTheme.colorScheme.onSurfaceVariant
                                    )
                                }
                                Text(
                                    text = "${order.totalPrice} AZN",
                                    style = MaterialTheme.typography.titleMedium,
                                    color = MaterialTheme.colorScheme.primary
                                )
                            }
                        }
                    }
                }
            }
        }
    }
}
