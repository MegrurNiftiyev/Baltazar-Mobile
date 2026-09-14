package com.example.baltazar.feature.order.data.model.dto

import com.example.baltazar.feature.order.domain.model.NextScreenResult
import com.example.baltazar.feature.order.domain.model.NextScreenType
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class NextScreenDataDto(
    @SerialName("screen") val screen: String? = null,
    @SerialName("order") val order: OrderDto? = null
) {
    fun toDomain(): NextScreenResult {
        return NextScreenResult(
            screen = NextScreenType.fromRaw(screen),
            order = order?.toDomain()
        )
    }
}
