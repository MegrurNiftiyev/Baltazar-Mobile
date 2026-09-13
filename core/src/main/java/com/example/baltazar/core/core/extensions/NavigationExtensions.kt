package com.example.baltazar.core.core.extensions

import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.navigation.NavBackStackEntry
import androidx.navigation.NavController
import com.example.baltazar.core.core.enums.ServiceType
import com.example.baltazar.core.core.navigation.CompanyDetail
import com.example.baltazar.core.core.navigation.FoodCompanyDetail
import com.example.baltazar.core.core.navigation.FoodDetail
import com.example.baltazar.core.core.navigation.FoodList
import com.example.baltazar.core.core.navigation.HotelCompanyDetail
import com.example.baltazar.core.core.navigation.HotelDetail
import com.example.baltazar.core.core.navigation.HotelList
import com.example.baltazar.core.core.navigation.RentACarCompanyDetail
import com.example.baltazar.core.core.navigation.RentACarDetail
import com.example.baltazar.core.core.navigation.RentACarList
import com.example.baltazar.core.core.navigation.TravelCompanyDetail
import com.example.baltazar.core.core.navigation.TravelDetail
import com.example.baltazar.core.core.navigation.TravelList
import com.example.baltazar.core.domain.model.ServiceCardItem

/**
 * Navigates to the appropriate service list screen based on [ServiceType].
 */
fun NavController.navigateToServiceList(serviceType: ServiceType) {
    when (serviceType) {
        ServiceType.RENT_A_CAR -> navigate(RentACarList)
        ServiceType.HOTEL -> navigate(HotelList)
        ServiceType.TRAVEL -> navigate(TravelList)
        ServiceType.FOOD -> navigate(FoodList)
        ServiceType.UNKNOWN -> {}
    }
}

/**
 * Navigates to the appropriate service detail screen based on [ServiceType] and service ID.
 */
fun NavController.navigateToServiceDetail(serviceType: ServiceType, serviceId: String) {
    when (serviceType) {
        ServiceType.RENT_A_CAR -> navigate(RentACarDetail(serviceId))
        ServiceType.HOTEL -> navigate(HotelDetail(serviceId))
        ServiceType.TRAVEL -> navigate(TravelDetail(serviceId))
        ServiceType.FOOD -> navigate(FoodDetail(serviceId))
        ServiceType.UNKNOWN -> {}
    }
}

/**
 * Navigates to the appropriate service detail screen for a [ServiceCardItem].
 */
fun NavController.navigateToServiceDetail(item: ServiceCardItem) {
    navigateToServiceDetail(item.serviceType, item.serviceId)
}

/**
 * Navigates to the appropriate company detail screen based on [ServiceType] and company ID.
 */
fun NavController.navigateToCompanyDetail(serviceType: ServiceType?, companyId: String) {
    when (serviceType) {
        ServiceType.FOOD -> navigate(FoodCompanyDetail(id = companyId))
        ServiceType.TRAVEL -> navigate(TravelCompanyDetail(id = companyId))
        ServiceType.RENT_A_CAR -> navigate(RentACarCompanyDetail(id = companyId))
        ServiceType.HOTEL -> navigate(HotelCompanyDetail(id = companyId))
        null, ServiceType.UNKNOWN -> navigate(CompanyDetail(id = companyId))
    }
}

/**
 * Sets a result on the previous backstack entry's savedStateHandle.
 */
fun <T> NavController.setPreviousResult(key: String, value: T) {
    previousBackStackEntry?.savedStateHandle?.set(key, value)
}

/**
 * Sets a result on the previous backstack entry and pops the backstack.
 */
fun <T> NavController.popBackStackWithResult(key: String, value: T) {
    setPreviousResult(key, value)
    popBackStack()
}

/**
 * Safely consumes (reads and automatically removes) a navigation result from [NavBackStackEntry].
 */
@Composable
fun <T> NavBackStackEntry.consumeResult(key: String): T? {
    val handle = this.savedStateHandle
    val flow = remember(key, handle) { handle.getStateFlow<T?>(key, null) }
    val value by flow.collectAsState()
    LaunchedEffect(value) {
        if (value != null) {
            handle.remove<T>(key)
        }
    }
    return value
}
