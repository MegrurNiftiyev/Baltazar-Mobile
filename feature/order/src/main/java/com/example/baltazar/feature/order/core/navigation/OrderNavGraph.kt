package com.example.baltazar.feature.order.core.navigation

import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.compose.composable
import androidx.navigation.toRoute
import com.example.baltazar.core.core.navigation.OrderConfirm
import com.example.baltazar.core.core.navigation.OrderFlow
import com.example.baltazar.core.core.navigation.OrderMapDeliverySelection
import com.example.baltazar.core.core.navigation.OrderPayment
import com.example.baltazar.feature.order.ui.screens.confirm.ConfirmScreen
import com.example.baltazar.feature.order.ui.screens.map_delivery_selection.MapDeliverySelectionScreen
import com.example.baltazar.feature.order.ui.screens.order_flow.OrderFlowScreen
import com.example.baltazar.feature.order.ui.screens.orders.OrdersScreen
import com.example.baltazar.feature.order.ui.screens.payment.PaymentScreen

import com.example.baltazar.core.core.navigation.OrderDetail
import com.example.baltazar.core.core.navigation.OrderUnknownScreenFallback
import com.example.baltazar.core.core.navigation.Orders
import com.example.baltazar.core.core.navigation.ProfileDriverLicense
import com.example.baltazar.core.core.navigation.ProfilePassport
import com.example.baltazar.core.core.navigation.ProfilePersonalInfo
import com.example.baltazar.feature.order.domain.model.NextScreenType
import com.example.baltazar.feature.order.ui.screens.order_detail.OrderDetailScreen
import com.example.baltazar.feature.order.ui.screens.unknown_screen_fallback.UnknownScreenFallbackScreen

fun NavHostController.navigateToNextScreen(screenType: NextScreenType, orderId: String) {
    val isFromOrderFlow = currentBackStackEntry?.destination?.route?.contains("OrderFlow") == true
    val popUpBlock: androidx.navigation.NavOptionsBuilder.() -> Unit = {
        if (isFromOrderFlow) {
            popUpTo<OrderFlow> { inclusive = true }
        }
    }

    when (screenType) {
        NextScreenType.PERSONAL_INFO_SCREEN -> navigate(ProfilePersonalInfo(isFromOrder = true), popUpBlock)
        NextScreenType.DRIVER_LICENSE_SCREEN -> navigate(ProfileDriverLicense(isFromOrder = true), popUpBlock)
        NextScreenType.PASSPORT_INFO_SCREEN -> navigate(ProfilePassport(isFromOrder = true), popUpBlock)
        NextScreenType.DELIVERY_ADDRESS_SCREEN -> navigate(OrderMapDeliverySelection(orderId = orderId), popUpBlock)
        NextScreenType.PAYMENT_SCREEN -> navigate(OrderPayment(orderId = orderId), popUpBlock)
        NextScreenType.CONFIRM_SCREEN -> navigate(OrderConfirm, popUpBlock)
        NextScreenType.UNKNOWN -> navigate(OrderUnknownScreenFallback, popUpBlock)
    }
}

fun NavGraphBuilder.orderNavGraph(navController: NavHostController) {
    composable<Orders> {
        OrdersScreen(
            navController = navController,
            onNavigateNext = { screenType, orderId ->
                navController.navigateToNextScreen(screenType, orderId)
            }
        )
    }

    composable<OrderFlow> {
        OrderFlowScreen(
            navController = navController,
            onNavigateNext = { screenType, orderId ->
                navController.navigateToNextScreen(screenType, orderId)
            }
        )
    }

    composable<OrderMapDeliverySelection> { backStackEntry ->
        val route = backStackEntry.toRoute<OrderMapDeliverySelection>()
        MapDeliverySelectionScreen(
            navController = navController,
            onNextScreen = { screenName ->
                val nextType = try { NextScreenType.valueOf(screenName) } catch (_: Exception) { NextScreenType.UNKNOWN }
                navController.navigateToNextScreen(nextType, route.orderId)
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

    composable<OrderDetail> {
        OrderDetailScreen(
            navController = navController,
            onNavigateNext = { screenType, orderId ->
                navController.navigateToNextScreen(screenType, orderId)
            }
        )
    }

    composable<OrderUnknownScreenFallback> {
        UnknownScreenFallbackScreen(navController = navController)
    }
}
