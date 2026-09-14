package com.example.baltazar.feature.order.ui.screens.order_detail

import androidx.compose.foundation.background
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
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.foundation.clickable
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
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
import com.example.baltazar.core.core.constants.BorderRadiuses
import com.example.baltazar.core.core.constants.IconSizes
import com.example.baltazar.core.core.constants.Paddings
import com.example.baltazar.core.core.constants.Spaces
import com.example.baltazar.core.core.enums.TitleAlignment
import com.example.baltazar.core.core.extensions.navigateToServiceDetail
import com.example.baltazar.core.core.utils.AppSnackbar
import com.example.baltazar.feature.order.domain.model.NextScreenType
import com.example.baltazar.feature.order.domain.model.OrderStatus

@Composable
fun OrderDetailScreen(
    navController: NavHostController,
    onNavigateNext: (NextScreenType, String) -> Unit = { _, _ -> },
    viewModel: OrderDetailViewModel = hiltViewModel()
) {
    val state by viewModel.state.collectAsState()
    val order = state.order

    LaunchedEffect(state.errorMessage) {
        state.errorMessage?.let { errorMsg ->
            AppSnackbar.error(errorMsg)
        }
    }

    Scaffold(
        topBar = {
            CustomAppBar(
                title = stringResource(R.string.order_detail_title),
                alignment = TitleAlignment.CENTER,
                onBackClick = { navController.popBackStack() }
            )
        },
        containerColor = MaterialTheme.colorScheme.background
    ) { paddingValues ->
        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
        ) {
            if (state.isLoading) {
                CircularProgressIndicator(
                    modifier = Modifier.align(Alignment.Center),
                    color = MaterialTheme.colorScheme.primary
                )
            } else if (state.errorMessage != null && order == null) {
                Text(
                    text = state.errorMessage ?: stringResource(R.string.generic_error),
                    color = MaterialTheme.colorScheme.error,
                    style = MaterialTheme.typography.bodyLarge,
                    modifier = Modifier
                        .align(Alignment.Center)
                        .padding(Paddings.Medium)
                )
            } else if (order != null) {
                Column(
                    modifier = Modifier
                        .fillMaxSize()
                        .verticalScroll(rememberScrollState())
                        .padding(Paddings.Medium),
                    verticalArrangement = Arrangement.spacedBy(Spaces.Medium)
                ) {
                    // Header Service Info Card
                    Surface(
                        shape = RoundedCornerShape(BorderRadiuses.Medium),
                        color = MaterialTheme.colorScheme.surfaceContainerLow,
                        modifier = Modifier
                            .fillMaxWidth()
                            .clickable {
                                if (order.serviceId.isNotBlank()) {
                                    navController.navigateToServiceDetail(order.serviceType, order.serviceId)
                                }
                            }
                    ) {
                        Row(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(Paddings.Medium),
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            val imageUrl = order.serviceItemSnapshot?.image ?: ""
                            val currency = order.serviceItemSnapshot?.currency ?: ""
                            val formattedPrice = if (currency.isNotBlank()) "${order.totalPrice} $currency" else "${order.totalPrice}"

                            if (imageUrl.isNotBlank()) {
                                AsyncImage(
                                    model = imageUrl,
                                    contentDescription = null,
                                    contentScale = ContentScale.Crop,
                                    modifier = Modifier
                                        .size(72.dp)
                                        .clip(RoundedCornerShape(BorderRadiuses.Medium))
                                        .background(MaterialTheme.colorScheme.outlineVariant)
                                )
                                Spacer(modifier = Modifier.width(Spaces.Medium))
                            }

                            Column(modifier = Modifier.weight(1f)) {
                                Text(
                                    text = order.serviceItemSnapshot?.title ?: "Order #${order.id.takeLast(6)}",
                                    style = MaterialTheme.typography.titleMedium,
                                    fontWeight = FontWeight.Bold,
                                    color = MaterialTheme.colorScheme.onSurface
                                )
                                Spacer(modifier = Modifier.height(Spaces.ExtraSmall))
                                Text(
                                    text = order.serviceType.name,
                                    style = MaterialTheme.typography.bodySmall,
                                    color = MaterialTheme.colorScheme.onSurfaceVariant
                                )
                                Spacer(modifier = Modifier.height(Spaces.ExtraSmall))
                                Text(
                                    text = formattedPrice,
                                    style = MaterialTheme.typography.titleLarge,
                                    color = MaterialTheme.colorScheme.primary,
                                    fontWeight = FontWeight.Bold
                                )
                            }
                        }
                    }

                    // Order Status Badge & Detail Section
                    Surface(
                        shape = RoundedCornerShape(BorderRadiuses.Medium),
                        color = MaterialTheme.colorScheme.surfaceContainerLow,
                        modifier = Modifier.fillMaxWidth()
                    ) {
                        Column(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(Paddings.Medium),
                            verticalArrangement = Arrangement.spacedBy(Spaces.Small)
                        ) {
                            Row(
                                modifier = Modifier.fillMaxWidth(),
                                horizontalArrangement = Arrangement.SpaceBetween,
                                verticalAlignment = Alignment.CenterVertically
                            ) {
                                Text(
                                    text = stringResource(R.string.order_status_label),
                                    style = MaterialTheme.typography.bodyMedium,
                                    color = MaterialTheme.colorScheme.onSurfaceVariant
                                )

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
                                        style = MaterialTheme.typography.labelMedium,
                                        color = statusColor,
                                        fontWeight = FontWeight.Bold,
                                        modifier = Modifier.padding(horizontal = Paddings.Small, vertical = Paddings.ExtraSmall)
                                    )
                                }
                            }

                            HorizontalDivider(color = MaterialTheme.colorScheme.outlineVariant)

                            Row(
                                modifier = Modifier.fillMaxWidth(),
                                horizontalArrangement = Arrangement.SpaceBetween
                            ) {
                                Text(
                                    text = stringResource(R.string.order_id_label),
                                    style = MaterialTheme.typography.bodyMedium,
                                    color = MaterialTheme.colorScheme.onSurfaceVariant
                                )
                                Text(
                                    text = order.id,
                                    style = MaterialTheme.typography.bodyMedium,
                                    color = MaterialTheme.colorScheme.onSurface,
                                    fontWeight = FontWeight.Medium
                                )
                            }

                            if (order.createdAt.isNotBlank()) {
                                Row(
                                    modifier = Modifier.fillMaxWidth(),
                                    horizontalArrangement = Arrangement.SpaceBetween
                                ) {
                                    Text(
                                        text = stringResource(R.string.order_date_label),
                                        style = MaterialTheme.typography.bodyMedium,
                                        color = MaterialTheme.colorScheme.onSurfaceVariant
                                    )
                                    Text(
                                        text = order.createdAt,
                                        style = MaterialTheme.typography.bodyMedium,
                                        color = MaterialTheme.colorScheme.onSurface
                                    )
                                }
                            }
                        }
                    }

                    // Personal Info Section
                    order.personalInfo?.let { personal ->
                        Surface(
                            shape = RoundedCornerShape(BorderRadiuses.Medium),
                            color = MaterialTheme.colorScheme.surfaceContainerLow,
                            modifier = Modifier.fillMaxWidth()
                        ) {
                            Column(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .padding(Paddings.Medium),
                                verticalArrangement = Arrangement.spacedBy(Spaces.Small)
                            ) {
                                Text(
                                    text = stringResource(R.string.personal_info_title),
                                    style = MaterialTheme.typography.titleSmall,
                                    color = MaterialTheme.colorScheme.onSurfaceVariant
                                )
                                Text(
                                    text = personal.name,
                                    style = MaterialTheme.typography.bodyMedium,
                                    color = MaterialTheme.colorScheme.onSurface
                                )
                                Text(
                                    text = personal.phone,
                                    style = MaterialTheme.typography.bodySmall,
                                    color = MaterialTheme.colorScheme.onSurfaceVariant
                                )
                            }
                        }
                    }

                    // Delivery Address Section
                    order.deliveryAddress?.let { address ->
                        Surface(
                            shape = RoundedCornerShape(BorderRadiuses.Medium),
                            color = MaterialTheme.colorScheme.surfaceContainerLow,
                            modifier = Modifier.fillMaxWidth()
                        ) {
                            Column(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .padding(Paddings.Medium),
                                verticalArrangement = Arrangement.spacedBy(Spaces.Small)
                            ) {
                                Text(
                                    text = stringResource(R.string.address_label),
                                    style = MaterialTheme.typography.titleSmall,
                                    color = MaterialTheme.colorScheme.onSurfaceVariant
                                )
                                Text(
                                    text = address.addressName,
                                    style = MaterialTheme.typography.bodyMedium,
                                    color = MaterialTheme.colorScheme.onSurface
                                )
                            }
                        }
                    }

                    Spacer(modifier = Modifier.height(Spaces.Medium))

                    // Action Buttons
                    val isPending = order.status == OrderStatus.PENDING || order.status == OrderStatus.AWAITING_PAYMENT || order.status == OrderStatus.PROCESSING
                    val isCancellable = isPending || order.status != OrderStatus.CANCELLED

                    if (isPending) {
                        Button(
                            onClick = { viewModel.continueOrderFlow(onNavigateNext) },
                            shape = RoundedCornerShape(BorderRadiuses.Huge),
                            colors = ButtonDefaults.buttonColors(
                                containerColor = MaterialTheme.colorScheme.primary,
                                contentColor = MaterialTheme.colorScheme.onPrimary
                            ),
                            modifier = Modifier
                                .fillMaxWidth()
                                .height(Paddings.ColossalMinus)
                        ) {
                            Text(
                                text = stringResource(R.string.continue_order),
                                style = MaterialTheme.typography.titleMedium
                            )
                        }
                    }

                    if (isCancellable && order.status != OrderStatus.CANCELLED) {
                        OutlinedButton(
                            onClick = { viewModel.cancelOrder() },
                            enabled = !state.isCancelling,
                            shape = RoundedCornerShape(BorderRadiuses.Huge),
                            colors = ButtonDefaults.outlinedButtonColors(
                                contentColor = MaterialTheme.colorScheme.error
                            ),
                            modifier = Modifier
                                .fillMaxWidth()
                                .height(Paddings.ColossalMinus)
                        ) {
                            if (state.isCancelling) {
                                CircularProgressIndicator(
                                    modifier = Modifier.size(IconSizes.Medium),
                                    color = MaterialTheme.colorScheme.error,
                                    strokeWidth = BorderRadiuses.ExtraMini
                                )
                            } else {
                                Text(
                                    text = stringResource(R.string.cancel_order),
                                    style = MaterialTheme.typography.titleMedium
                                )
                            }
                        }
                    }
                }
            }
        }
    }
}
