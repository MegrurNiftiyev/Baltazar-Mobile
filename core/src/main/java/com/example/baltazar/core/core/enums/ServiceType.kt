package com.example.baltazar.core.core.enums

import androidx.annotation.StringRes
import com.example.baltazar.core.R
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
enum class ServiceType {
    @SerialName("RENT_A_CAR") RENT_A_CAR,
    @SerialName("HOTEL") HOTEL,
    @SerialName("TRAVEL") TRAVEL,
    @SerialName("FOOD") FOOD,
    @SerialName("UNKNOWN") UNKNOWN;

    companion object {
        fun fromRaw(type: String?, category: String? = null): ServiceType {
            val raw = (type?.takeIf { it.isNotBlank() } ?: category?.takeIf { it.isNotBlank() })?.uppercase()?.trim()
                ?: return FOOD
            return when (raw) {
                "HOTEL", "OTEL" -> HOTEL
                "RENT_A_CAR", "RENT A CAR", "AVTO", "CAR" -> RENT_A_CAR
                "TRAVEL", "TUR", "TOUR", "SƏYAHƏT" -> TRAVEL
                "FOOD", "YEMƏK", "RESTAURANT" -> FOOD
                else -> try {
                    valueOf(raw)
                } catch (e: Exception) {
                    FOOD
                }
            }
        }
    }
}

@StringRes
fun ServiceType.getDisplayNameResId(): Int {
    return when (this) {
        ServiceType.RENT_A_CAR -> R.string.service_rent_a_car
        ServiceType.HOTEL -> R.string.service_hotel
        ServiceType.TRAVEL -> R.string.service_travel
        ServiceType.FOOD -> R.string.service_food
        ServiceType.UNKNOWN -> R.string.service_food
    }
}

