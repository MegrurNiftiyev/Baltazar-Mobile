package com.example.baltazar.feature.order.core.navigation

import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.compose.composable
import com.example.baltazar.core.core.navigation.OrderConfirm
import com.example.baltazar.core.core.navigation.OrderFlow
import com.example.baltazar.core.core.navigation.OrderMapDeliverySelection
import com.example.baltazar.core.core.navigation.OrderPayment
import com.example.baltazar.core.core.navigation.OrderSummary
import com.example.baltazar.core.core.navigation.Orders
import com.example.baltazar.core.core.navigation.ProfileDriverLicense
import com.example.baltazar.core.core.navigation.ProfilePassport
import com.example.baltazar.core.core.navigation.ProfilePersonalInfo
import com.example.baltazar.feature.order.domain.model.NextScreenType
import com.example.baltazar.feature.order.ui.screens.confirm.ConfirmScreen
import com.example.baltazar.feature.order.ui.screens.map_delivery_selection.MapDeliverySelectionScreen
import com.example.baltazar.feature.order.ui.screens.order_summary.OrderSummaryScreen
import com.example.baltazar.feature.order.ui.screens.orders.OrdersScreen
import com.example.baltazar.feature.order.ui.screens.payment.PaymentScreen

fun NavHostController.navigateToNextScreen(screenType: NextScreenType, orderId: String) {
    when (screenType) {
        NextScreenType.PERSONAL_INFO_SCREEN -> navigate(ProfilePersonalInfo)
        NextScreenType.DRIVER_LICENSE_SCREEN -> navigate(ProfileDriverLicense)
        NextScreenType.PASSPORT_INFO_SCREEN -> navigate(ProfilePassport)
        NextScreenType.DELIVERY_ADDRESS_SCREEN -> navigate(OrderMapDeliverySelection)
        NextScreenType.PAYMENT_SCREEN -> navigate(OrderPayment)
        NextScreenType.CONFIRM_SCREEN -> navigate(OrderConfirm)
        NextScreenType.UNKNOWN -> {}
    }
}

fun NavGraphBuilder.orderNavGraph(navController: NavHostController) {
    composable<Orders> {
        OrdersScreen(navController = navController)
    }

    composable<OrderFlow> {
        OrderSummaryScreen(
            navController = navController,
            onNavigateNext = { screenType, orderId ->
                navController.navigateToNextScreen(screenType, orderId)
            }
        )
    }

    composable<OrderMapDeliverySelection> {
        MapDeliverySelectionScreen(
            navController = navController,
            onNextScreen = { screenName ->
                val nextType = try { NextScreenType.valueOf(screenName) } catch (_: Exception) { NextScreenType.PAYMENT_SCREEN }
                navController.navigateToNextScreen(nextType, "")
            }
        )
    }

    composable<OrderSummary> {
        OrderSummaryScreen(
            navController = navController,
            onNavigateNext = { screenType, orderId ->
                navController.navigateToNextScreen(screenType, orderId)
            }
        )
    }

    composable<OrderPayment> {
        PaymentScreen(
            navController = navController,
            onPaymentSuccessNavigate = {
                navController.navigate(OrderConfirm)
            }
        )
    }

    composable<OrderConfirm> {
        ConfirmScreen(navController = navController)
    }
}
