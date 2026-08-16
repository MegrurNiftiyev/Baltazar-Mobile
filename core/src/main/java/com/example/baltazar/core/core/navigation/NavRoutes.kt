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
object Login

@Serializable
object Register

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
object FoodList

@Serializable
data class FoodDetail(val id: String)

@Serializable
data class OrderFlow(val serviceType: String, val serviceId: String)

@Serializable
object Orders

// Profile Sub-Screens
@Serializable
object ProfilePersonalInfo

@Serializable
object ProfileDriverLicense

@Serializable
object ProfilePassport

@Serializable
object ProfileUserDetail

// Order Flow Sub-Screens
@Serializable
object OrderPersonalInfo

@Serializable
object OrderPassportInfo

@Serializable
object OrderDriverLicense

@Serializable
object OrderDeliveryAddress

@Serializable
object OrderAddressSelection

@Serializable
object OrderMapDeliverySelection

@Serializable
object OrderSummary

@Serializable
object OrderPayment

@Serializable
object OrderConfirm


