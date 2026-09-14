package com.example.baltazar.core.core.navigation

import com.example.baltazar.core.core.enums.HomeTab
import kotlinx.serialization.Serializable

@Serializable
object Splash

@Serializable
object Onboarding

@Serializable
data class Home(val initialTab: HomeTab = HomeTab.Explore)

@Serializable
data class Login(val isPopStack: Boolean = false)

@Serializable
data class Register(val isPopStack: Boolean = false)

@Serializable
object AuthSelection

@Serializable
object Explore

@Serializable
object Wishlist

@Serializable
object Profile

@Serializable
object Cart

@Serializable
object Notifications

@Serializable
object Settings

@Serializable
object About

@Serializable
object Help

@Serializable
object RentACarList

@Serializable
data class RentACarDetail(val id: String)

@Serializable
object HotelList

@Serializable
data class HotelDetail(val id: String)

@Serializable
object TravelList

@Serializable
data class TravelDetail(val id: String)

@Serializable
data class TourRoadmap(val tourId: String)

@Serializable
object FoodCompanyList

@Serializable
data class FoodCompanyDetail(val id: String)

@Serializable
object HotelCompanyList

@Serializable
data class HotelCompanyDetail(val id: String)

@Serializable
object TravelCompanyList

@Serializable
data class TravelCompanyDetail(val id: String)

@Serializable
object RentACarCompanyList

@Serializable
data class RentACarCompanyDetail(val id: String)

@Serializable
data class CompanyList(val serviceType: String? = null)

@Serializable
data class CompanyDetail(val id: String)

@Serializable
object FoodList

@Serializable
data class FoodDetail(val id: String)

@Serializable
data class OrderFlow(
    val serviceType: String? = null,
    val serviceId: String? = null,
    val orderId: String? = null
)

@Serializable
object Orders

// Profile Sub-Screens
@Serializable
data class ProfilePersonalInfo(val isFromOrder: Boolean = false, val orderId: String? = null)

@Serializable
data class ProfileDriverLicense(val isFromOrder: Boolean = false, val orderId: String? = null)

@Serializable
data class ProfilePassport(val isFromOrder: Boolean = false, val orderId: String? = null)

@Serializable
object ProfileUserDetail

// Order Flow Sub-Screens
@Serializable
object OrderDriverLicense

@Serializable
data class OrderMapDeliverySelection(val orderId: String = "")

@Serializable
data class OrderPayment(val orderId: String = "")

@Serializable
object OrderConfirm

@Serializable
data class OrderDetail(val orderId: String)

@Serializable
object OrderUnknownScreenFallback


