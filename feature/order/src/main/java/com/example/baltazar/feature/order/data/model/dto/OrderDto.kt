package com.example.baltazar.feature.order.data.model.dto

import com.example.baltazar.core.core.enums.ServiceType
import com.example.baltazar.feature.order.domain.model.Order
import com.example.baltazar.feature.order.domain.model.OrderStatus
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class OrderDto(
    @SerialName("id") val id: String? = null,
    @SerialName("_id") val mongoId: String? = null,
    @SerialName("userId") val userId: String? = null,
    @SerialName("serviceType") val serviceType: String? = null,
    @SerialName("serviceId") val serviceId: String? = null,
    @SerialName("subItemId") val subItemId: String? = null,
    @SerialName("status") val status: String? = null,
    @SerialName("isPersonalInfoRequired") val isPersonalInfoRequired: Boolean? = null,
    @SerialName("isDriverLicenseRequired") val isDriverLicenseRequired: Boolean? = null,
    @SerialName("isPassportRequired") val isPassportRequired: Boolean? = null,
    @SerialName("isDeliveryAddressRequired") val isDeliveryAddressRequired: Boolean? = null,
    @SerialName("personalInfo") val personalInfo: OrderPersonalInfoDto? = null,
    @SerialName("driverLicense") val driverLicense: OrderDriverLicenseDto? = null,
    @SerialName("passport") val passport: OrderPassportInfoDto? = null,
    @SerialName("paymentMethodId") val paymentMethodId: String? = null,
    @SerialName("deliveryAddress") val deliveryAddress: DeliveryAddressDto? = null,
    @SerialName("serviceItemSnapshot") val serviceItemSnapshot: OrderServiceItemSnapshotDto? = null,
    @SerialName("totalPrice") val totalPrice: Double? = null,
    @SerialName("createdAt") val createdAt: String? = null,
    @SerialName("expiresAt") val expiresAt: String? = null,
    @SerialName("paidAt") val paidAt: String? = null,
    @SerialName("updatedAt") val updatedAt: String? = null
) {
    fun toDomain(): Order {
        return Order(
            id = id ?: mongoId ?: "",
            userId = userId.orEmpty(),
            serviceType = ServiceType.fromRaw(serviceType),
            serviceId = serviceId.orEmpty(),
            subItemId = subItemId,
            status = OrderStatus.fromRaw(status),
            isPersonalInfoRequired = isPersonalInfoRequired ?: false,
            isDriverLicenseRequired = isDriverLicenseRequired ?: false,
            isPassportRequired = isPassportRequired ?: false,
            isDeliveryAddressRequired = isDeliveryAddressRequired ?: false,
            personalInfo = personalInfo?.toDomain(),
            driverLicense = driverLicense?.toDomain(),
            passport = passport?.toDomain(),
            paymentMethodId = paymentMethodId,
            deliveryAddress = deliveryAddress?.toDomain(),
            serviceItemSnapshot = serviceItemSnapshot?.toDomain(),
            totalPrice = totalPrice ?: 0.0,
            createdAt = createdAt.orEmpty(),
            expiresAt = expiresAt,
            paidAt = paidAt,
            updatedAt = updatedAt
        )
    }
}
