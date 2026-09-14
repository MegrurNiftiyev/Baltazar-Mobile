package com.example.baltazar.feature.order.domain.model

data class OrderServiceItemSnapshot(
    val title: String,
    val image: String?,
    val price: Double,
    val priceSuffix: String?,
    val currency: String
)
