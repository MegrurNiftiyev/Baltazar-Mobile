package com.example.baltazar.core.domain.model

import com.example.baltazar.core.core.enums.ServiceType

data class AddToWishlistInfo(
    val serviceId: String = "",
    val serviceType: ServiceType = ServiceType.UNKNOWN
)
