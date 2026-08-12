package com.example.baltazar.feature.explore.domain.model

import com.example.baltazar.core.enums.ServiceType

data class BannerItem(
    val id: String,
    val serviceType: ServiceType,
    val title: String,
    val desc: String,
    val order: Int,
    val isActive: Boolean,
    val image: String,
    val createdAt: String
)
