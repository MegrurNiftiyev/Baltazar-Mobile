package com.example.baltazar.feature.order.ui.screens.order_detail

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import com.example.baltazar.core.R
import com.example.baltazar.core.core.components.CustomAppBar
import com.example.baltazar.core.core.constants.Paddings
import com.example.baltazar.core.core.constants.Spaces
import com.example.baltazar.core.core.enums.TitleAlignment
import com.example.baltazar.core.core.extensions.navigateToServiceDetail
import com.example.baltazar.core.core.utils.AppSnackbar
import com.example.baltazar.feature.order.domain.model.NextScreenType
import com.example.baltazar.feature.order.ui.screens.order_detail.components.DeliveryAddressCard
import com.example.baltazar.feature.order.ui.screens.order_detail.components.OrderActions
import com.example.baltazar.feature.order.ui.screens.order_detail.components.OrderServiceCard
import com.example.baltazar.feature.order.ui.screens.order_detail.components.OrderStatusCard
import com.example.baltazar.feature.order.ui.screens.order_detail.components.PersonalInfoCard

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
                    OrderServiceCard(
                        order = order,
                        onClick = {
                            if (order.serviceId.isNotBlank()) {
                                navController.navigateToServiceDetail(order.serviceType, order.serviceId)
                            }
                        }
                    )

                    OrderStatusCard(order = order)

                    order.personalInfo?.let { personal ->
                        PersonalInfoCard(personalInfo = personal)
                    }

                    order.deliveryAddress?.let { address ->
                        DeliveryAddressCard(deliveryAddress = address)
                    }

                    OrderActions(
                        order = order,
                        isCancelling = state.isCancelling,
                        onContinue = { viewModel.continueOrderFlow(onNavigateNext) },
                        onCancel = viewModel::cancelOrder
                    )
                }
            }
        }
    }
}
