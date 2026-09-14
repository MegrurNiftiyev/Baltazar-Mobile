package com.example.baltazar.feature.order.domain.model

data class NextScreenResult(
    val screen: NextScreenType,
    val order: Order? = null
)
