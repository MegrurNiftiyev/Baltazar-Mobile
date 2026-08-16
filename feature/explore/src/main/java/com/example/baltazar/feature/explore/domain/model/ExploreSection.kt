package com.example.baltazar.feature.explore.domain.model

import com.example.baltazar.core.core.enums.ServiceType
import com.example.baltazar.core.domain.model.ServiceCardItem

data class ExploreSection(
    val serviceType: ServiceType,
    val title: String,
    val items: List<ServiceCardItem>,
    val order: Int
)
