package com.example.baltazar.feature.explore.domain.model

import androidx.compose.ui.graphics.vector.ImageVector
import com.example.baltazar.core.core.enums.ServiceType

data class QuickActionItem(
    val titleRes: Int,
    val icon: ImageVector,
    val serviceType: ServiceType
)
