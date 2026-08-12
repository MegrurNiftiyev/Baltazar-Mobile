package com.example.baltazar.core.data.model.request

import com.example.baltazar.core.domain.model.ProfileImage
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class UpdateProfilePhotoDto(
    @SerialName("avatarUrl") val avatarUrl: String? = null
)

fun ProfileImage.toDto(): UpdateProfilePhotoDto {
    return UpdateProfilePhotoDto(
        avatarUrl = imageUrl
    )
}
