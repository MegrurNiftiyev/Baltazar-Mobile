package com.example.baltazar.core.data.model.dto

import com.example.baltazar.core.domain.model.ReviewItem
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class ReviewDto(
    @SerialName("id") val id: String,
    @SerialName("userId") val userId: String? = null,
    @SerialName("userName") val userName: String? = "Anonymous",
    @SerialName("avatarUrl") val avatarUrl: String? = null,
    @SerialName("targetType") val targetType: String? = null,
    @SerialName("targetId") val targetId: String? = null,
    @SerialName("rating") val rating: Int = 0,
    @SerialName("comment") val comment: String = "",
    @SerialName("createdAt") val createdAt: String = "",
    @SerialName("updatedAt") val updatedAt: String? = null
) {
    fun toDomain(): ReviewItem = ReviewItem(
        id = id,
        userId = userId.orEmpty(),
        userName = if (userName.isNullOrBlank() || userName == "string") "İstifadəçi" else userName,
        userAvatar = avatarUrl,
        targetType = targetType.orEmpty(),
        targetId = targetId.orEmpty(),
        rating = rating.toDouble(),
        comment = comment,
        createdAt = createdAt
    )
}
