package com.example.baltazar.feature.hotel.data.model.dto

import com.example.baltazar.feature.hotel.domain.model.HotelRoom
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class HotelRoomDto(
    @SerialName("id") val id: String,
    @SerialName("hotelId") val hotelId: String? = null,
    @SerialName("roomType") val roomType: String? = null,
    @SerialName("name") val name: String? = null,
    @SerialName("price") val price: Double = 0.0,
    @SerialName("capacity") val capacity: Int = 1,
    @SerialName("amenities") val amenities: List<String> = emptyList(),
    @SerialName("status") val status: String? = null,
    @SerialName("image") val image: String? = null
) {
    fun toDomain(): HotelRoom = HotelRoom(
        id = id,
        hotelId = hotelId.orEmpty(),
        roomType = roomType.orEmpty(),
        name = name ?: roomType.orEmpty(),
        price = price,
        capacity = capacity,
        amenities = amenities,
        status = status.orEmpty(),
        image = image.orEmpty()
    )
}
