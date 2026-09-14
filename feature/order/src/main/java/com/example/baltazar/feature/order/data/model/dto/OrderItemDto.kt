package com.example.baltazar.feature.order.data.model.dto

import com.example.baltazar.core.core.enums.ServiceType
import com.example.baltazar.feature.order.domain.model.Order
import com.example.baltazar.feature.order.domain.model.OrderStatus
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class OrderItemDto(
    @SerialName("id") val id: String? = null,
    @SerialName("_id") val mongoId: String? = null,
    @SerialName("userId") val userId: String? = null,
    @SerialName("serviceType") val serviceType: String? = null,
    @SerialName("serviceId") val serviceId: String? = null,
    @SerialName("subItemId") val subItemId: String? = null,
    @SerialName("status") val status: String? = null,
    @SerialName("serviceItemSnapshot") val serviceItemSnapshot: OrderServiceItemSnapshotDto? = null,
    @SerialName("totalPrice") val totalPrice: Double? = null,
    @SerialName("createdAt") val createdAt: String? = null,
    @SerialName("expiresAt") val expiresAt: String? = null,
    @SerialName("paidAt") val paidAt: String? = null
) {
    fun toDomain(): Order {
        return Order(
            id = id ?: mongoId ?: "",
            userId = userId.orEmpty(),
            serviceType = ServiceType.fromRaw(serviceType),
            serviceId = serviceId.orEmpty(),
            subItemId = subItemId,
            status = OrderStatus.fromRaw(status),
            isPersonalInfoRequired = false,
            isDriverLicenseRequired = false,
            isPassportRequired = false,
            isDeliveryAddressRequired = false,
            personalInfo = null,
            driverLicense = null,
            passport = null,
            paymentMethodId = null,
            deliveryAddress = null,
            serviceItemSnapshot = serviceItemSnapshot?.toDomain(),
            totalPrice = totalPrice ?: 0.0,
            createdAt = createdAt.orEmpty(),
            expiresAt = expiresAt,
            paidAt = paidAt,
            updatedAt = null
        )
    }
}
