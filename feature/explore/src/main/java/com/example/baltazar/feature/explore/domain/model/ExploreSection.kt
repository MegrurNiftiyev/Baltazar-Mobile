package com.example.baltazar.feature.explore.domain.model

import com.example.baltazar.core.enums.ServiceType

data class ExploreSection(
    val serviceType: ServiceType,
    val title: String,
    val items: List<ExploreItem>,
    val order: Int
)
