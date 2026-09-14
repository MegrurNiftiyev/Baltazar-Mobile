package com.example.baltazar.feature.hotel.domain.model

data class HotelRoom(
    val id: String = "",
    val hotelId: String = "",
    val roomType: String = "",
    val name: String = "",
    val price: Double = 0.0,
    val capacity: Int = 1,
    val amenities: List<String> = emptyList(),
    val status: String = "",
    val image: String = ""
)
