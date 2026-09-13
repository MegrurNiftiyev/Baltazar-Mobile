package com.example.baltazar.core.core.navigation

import com.example.baltazar.core.core.enums.ServiceType
import java.io.Serializable

data class LikeResult(
    val itemId: String,
    val isLiked: Boolean,
    val serviceType: ServiceType? = null
) : Serializable
