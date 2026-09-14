package com.example.baltazar.feature.profile.data.model.request

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class UpdateAvatarRequest(
    @SerialName("avatar") val avatar: String
)
