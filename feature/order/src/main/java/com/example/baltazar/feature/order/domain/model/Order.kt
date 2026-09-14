package com.example.baltazar.feature.order.domain.model

import com.example.baltazar.core.core.enums.ServiceType

data class Order(
    val id: String,
    val userId: String,
    val serviceType: ServiceType,
    val serviceId: String,
    val subItemId: String?,
    val status: OrderStatus,
    val isPersonalInfoRequired: Boolean,
    val isDriverLicenseRequired: Boolean,
    val isPassportRequired: Boolean,
    val isDeliveryAddressRequired: Boolean,
    val personalInfo: OrderPersonalInfo?,
    val driverLicense: OrderDriverLicense?,
    val passport: OrderPassportInfo?,
    val paymentMethodId: String?,
    val deliveryAddress: DeliveryAddress?,
    val serviceItemSnapshot: OrderServiceItemSnapshot?,
    val totalPrice: Double,
    val createdAt: String,
    val expiresAt: String?,
    val paidAt: String?,
    val updatedAt: String?
)
