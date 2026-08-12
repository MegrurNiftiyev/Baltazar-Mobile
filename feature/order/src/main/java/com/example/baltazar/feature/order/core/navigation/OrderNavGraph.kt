package com.example.baltazar.feature.order.core.navigation

import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.compose.composable
import androidx.navigation.toRoute
import com.example.baltazar.core.core.navigation.OrderFlow
import com.example.baltazar.feature.order.ui.screens.address.AddressScreen
import com.example.baltazar.feature.order.ui.screens.confirm.ConfirmScreen
import com.example.baltazar.feature.order.ui.screens.delivery_address.DeliveryAddressScreen
import com.example.baltazar.feature.order.ui.screens.driver_license.DriverLicenseScreen
import com.example.baltazar.feature.order.ui.screens.passport_info.PassportInfoScreen
import com.example.baltazar.feature.order.ui.screens.payment.PaymentScreen
import com.example.baltazar.feature.order.ui.screens.personal_info.PersonalInfoScreen

fun NavGraphBuilder.orderNavGraph(navController: NavHostController) {
    composable<OrderFlow> { backStackEntry ->
        val args = backStackEntry.toRoute<OrderFlow>()
        // Note: For now we just show PersonalInfo as the entry of the flow
        // In a real scenario, we might use a nested NavHost here or multiple composables
        PersonalInfoScreen(navController)
    }
}

