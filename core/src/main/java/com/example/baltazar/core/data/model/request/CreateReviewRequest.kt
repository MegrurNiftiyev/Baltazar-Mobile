package com.example.baltazar.core.data.model.request

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class CreateReviewRequest(
    @SerialName("targetType") val targetType: String,
    @SerialName("targetId") val targetId: String,
    @SerialName("rating") val rating: Int,
    @SerialName("comment") val comment: String
)
