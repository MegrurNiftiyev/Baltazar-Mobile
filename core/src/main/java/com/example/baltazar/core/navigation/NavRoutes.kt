package com.example.baltazar.core.navigation

import kotlinx.serialization.Serializable

@Serializable
object Splash

@Serializable
object Onboarding

@Serializable
object Home

@Serializable
object Login

@Serializable
object Register

@Serializable
object Explore

@Serializable
object Wishlist

@Serializable
object Profile

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
object FoodCompanyList

@Serializable
data class FoodCompanyDetail(val id: String)

@Serializable
data class OrderFlow(val serviceType: String, val serviceId: String)