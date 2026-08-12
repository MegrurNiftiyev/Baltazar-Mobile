package com.example.baltazar.core.enums

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
enum class ServiceType {
    @SerialName("RENT_A_CAR") RENT_A_CAR,
    @SerialName("HOTEL") HOTEL,
    @SerialName("TRAVEL") TRAVEL,
    @SerialName("FOOD") FOOD,
    @SerialName("UNKNOWN") UNKNOWN
}
