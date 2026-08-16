package com.example.baltazar.feature.order.core.navigation

import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.compose.composable
import androidx.navigation.toRoute
import com.example.baltazar.core.core.navigation.OrderAddressSelection
import com.example.baltazar.core.core.navigation.OrderConfirm
import com.example.baltazar.core.core.navigation.OrderDeliveryAddress
import com.example.baltazar.core.core.navigation.OrderDriverLicense
import com.example.baltazar.core.core.navigation.OrderFlow
import com.example.baltazar.core.core.navigation.OrderMapDeliverySelection
import com.example.baltazar.core.core.navigation.OrderPassportInfo
import com.example.baltazar.core.core.navigation.OrderPayment
import com.example.baltazar.core.core.navigation.OrderPersonalInfo
import com.example.baltazar.core.core.navigation.OrderSummary
import com.example.baltazar.core.core.navigation.Orders
import com.example.baltazar.feature.order.ui.screens.address_selection.AddressSelectionScreen
import com.example.baltazar.feature.order.ui.screens.confirm.ConfirmScreen
import com.example.baltazar.feature.order.ui.screens.delivery_address.DeliveryAddressScreen
import com.example.baltazar.feature.order.ui.screens.driver_license.DriverLicenseScreen
import com.example.baltazar.feature.order.ui.screens.map_delivery_selection.MapDeliverySelectionScreen
import com.example.baltazar.feature.order.ui.screens.orders.OrdersScreen
import com.example.baltazar.feature.order.ui.screens.order_summary.OrderSummaryScreen
import com.example.baltazar.feature.order.ui.screens.passport_info.PassportInfoScreen
import com.example.baltazar.feature.order.ui.screens.payment.PaymentScreen
import com.example.baltazar.feature.order.ui.screens.personal_info.PersonalInfoScreen

fun NavGraphBuilder.orderNavGraph(navController: NavHostController) {
    composable<Orders> { OrdersScreen(navController) }
    composable<OrderFlow> { backStackEntry ->
        val args = backStackEntry.toRoute<OrderFlow>()
        PersonalInfoScreen(navController)
    }
    composable<OrderPersonalInfo> { PersonalInfoScreen(navController) }
    composable<OrderPassportInfo> { PassportInfoScreen(navController) }
    composable<OrderDriverLicense> { DriverLicenseScreen(navController) }
    composable<OrderDeliveryAddress> { DeliveryAddressScreen(navController) }
    composable<OrderAddressSelection> { AddressSelectionScreen(navController) }
    composable<OrderMapDeliverySelection> { MapDeliverySelectionScreen(navController) }
    composable<OrderSummary> { OrderSummaryScreen(navController) }
    composable<OrderPayment> { PaymentScreen(navController) }
    composable<OrderConfirm> { ConfirmScreen(navController) }
}



