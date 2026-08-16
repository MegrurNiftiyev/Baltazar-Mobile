package com.example.baltazar.core.data.model.request

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class UpdatePhotoRequestDto(
    @SerialName("avatarUrl") val avatarUrl: String
)
