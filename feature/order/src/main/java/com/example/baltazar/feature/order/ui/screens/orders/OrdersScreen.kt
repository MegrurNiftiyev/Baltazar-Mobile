package com.example.baltazar.feature.order.ui.screens.orders

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.combinedClickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.MoreVert
import androidx.compose.material.icons.automirrored.filled.ReceiptLong
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import coil.compose.AsyncImage
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
import com.example.baltazar.core.core.navigation.OrderDetail
import com.example.baltazar.feature.order.domain.model.NextScreenType
import com.example.baltazar.feature.order.domain.model.Order
import com.example.baltazar.feature.order.domain.model.OrderStatus
import compose.icons.TablerIcons
import compose.icons.tablericons.Receipt

@OptIn(ExperimentalFoundationApi::class, ExperimentalMaterial3Api::class)
@Composable
fun OrdersScreen(
    navController: NavHostController,
    onNavigateNext: (NextScreenType, String) -> Unit = { _, _ -> },
    onNavigateToTab: (HomeTab) -> Unit = {},
    viewModel: OrdersViewModel = hiltViewModel()
) {
    val state by viewModel.state.collectAsState()
    val isGuest = state.user.isGuest
    var selectedOrderForCancel by remember { mutableStateOf<Order?>(null) }

    if (selectedOrderForCancel != null) {
        val targetOrder = selectedOrderForCancel!!
        ModalBottomSheet(
            onDismissRequest = { selectedOrderForCancel = null }
        ) {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(Paddings.Medium),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.spacedBy(Spaces.Medium)
            ) {
                Text(
                    text = stringResource(R.string.cancel_order_dialog_title),
                    style = MaterialTheme.typography.titleLarge,
                    color = MaterialTheme.colorScheme.onSurface
                )
                Text(
                    text = stringResource(R.string.cancel_order_dialog_msg),
                    style = MaterialTheme.typography.bodyMedium,
                    color = MaterialTheme.colorScheme.onSurfaceVariant
                )
                Button(
                    onClick = {
                        val orderId = targetOrder.id
                        selectedOrderForCancel = null
                        viewModel.cancelOrder(orderId)
                    },
                    shape = RoundedCornerShape(BorderRadiuses.Huge),
                    colors = ButtonDefaults.buttonColors(
                        containerColor = MaterialTheme.colorScheme.error,
                        contentColor = MaterialTheme.colorScheme.onError
                    ),
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(Paddings.ColossalMinus)
                ) {
                    Text(
                        text = stringResource(R.string.cancel_order),
                        style = MaterialTheme.typography.titleMedium
                    )
                }
                Spacer(modifier = Modifier.height(Spaces.Small))
            }
        }
    }

    Scaffold(
        topBar = {
            CustomAppBar(
                title = stringResource(id = R.string.orders_title),
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
                            val isPending = order.status == OrderStatus.PENDING || order.status == OrderStatus.AWAITING_PAYMENT || order.status == OrderStatus.PROCESSING
                            val imageUrl = order.serviceItemSnapshot?.image ?: ""
                            val currency = order.serviceItemSnapshot?.currency ?: ""
                            val formattedPrice = if (currency.isNotBlank()) "${order.totalPrice} $currency" else "${order.totalPrice}"

                            Row(
                                verticalAlignment = Alignment.CenterVertically,
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .padding(vertical = Paddings.ExtraSmall)
                                    .clip(RoundedCornerShape(BorderRadiuses.Medium))
                                    .background(MaterialTheme.colorScheme.surfaceContainerLow)
                                    .combinedClickable(
                                        onClick = {
                                            navController.navigate(OrderDetail(order.id))
                                        },
                                        onLongClick = {
                                            if (order.status != OrderStatus.CANCELLED) {
                                                selectedOrderForCancel = order
                                            }
                                        }
                                    )
                                    .padding(Paddings.Medium)
                            ) {
                                if (imageUrl.isNotBlank()) {
                                    AsyncImage(
                                        model = imageUrl,
                                        contentDescription = null,
                                        contentScale = ContentScale.Crop,
                                        modifier = Modifier
                                            .size(56.dp)
                                            .clip(RoundedCornerShape(BorderRadiuses.Medium))
                                            .background(MaterialTheme.colorScheme.outlineVariant)
                                    )
                                } else {
                                    Box(
                                        modifier = Modifier
                                            .size(56.dp)
                                            .clip(RoundedCornerShape(BorderRadiuses.Medium))
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
                                }

                                Spacer(modifier = Modifier.width(Spaces.Medium))

                                Column(modifier = Modifier.weight(1f)) {
                                    Text(
                                        text = order.serviceItemSnapshot?.title ?: stringResource(id = R.string.order_number_format, order.id.takeLast(6)),
                                        style = MaterialTheme.typography.titleMedium,
                                        fontWeight = FontWeight.SemiBold,
                                        color = MaterialTheme.colorScheme.onSurface
                                    )
                                    Spacer(modifier = Modifier.height(Spaces.ExtraSmall))

                                    val statusColor = when (order.status) {
                                        OrderStatus.CONFIRMED -> MaterialTheme.colorScheme.primary
                                        OrderStatus.CANCELLED, OrderStatus.EXPIRED -> MaterialTheme.colorScheme.error
                                        else -> MaterialTheme.colorScheme.tertiary
                                    }

                                    Surface(
                                        shape = RoundedCornerShape(BorderRadiuses.Small),
                                        color = statusColor.copy(alpha = 0.12f)
                                    ) {
                                        Text(
                                            text = order.status.name,
                                            style = MaterialTheme.typography.labelSmall,
                                            color = statusColor,
                                            fontWeight = FontWeight.Bold,
                                            modifier = Modifier.padding(horizontal = Paddings.ExtraSmall, vertical = 2.dp)
                                        )
                                    }
                                }

                                Column(horizontalAlignment = Alignment.End) {
                                    Text(
                                        text = formattedPrice,
                                        style = MaterialTheme.typography.titleMedium,
                                        color = MaterialTheme.colorScheme.primary,
                                        fontWeight = FontWeight.Bold
                                    )
                                    if (order.status != OrderStatus.CANCELLED) {
                                        IconButton(
                                            onClick = { selectedOrderForCancel = order },
                                            modifier = Modifier.size(24.dp)
                                        ) {
                                            Icon(
                                                imageVector = Icons.Default.MoreVert,
                                                contentDescription = null,
                                                tint = MaterialTheme.colorScheme.onSurfaceVariant
                                            )
                                        }
                                    }
                                }
                            }
                        }
                    }
                }
            }
        }
    }
}

