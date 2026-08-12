package com.example.baltazar.core.domain.model

import com.example.baltazar.core.enums.ServiceType

data class ServiceSection(
    val serviceType: ServiceType,
    val title: String,
    val items: List<ServiceItem>
)
