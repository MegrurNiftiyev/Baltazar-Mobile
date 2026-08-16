package com.example.baltazar.core.data.model.dto

import com.example.baltazar.core.domain.model.ReviewEligibility
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class ReviewEligibilityDto(
    @SerialName("eligible") val eligible: Boolean = false,
    @SerialName("alreadyReviewed") val alreadyReviewed: Boolean = false,
    @SerialName("canSubmit") val canSubmit: Boolean = false
) {
    fun toDomain(): ReviewEligibility = ReviewEligibility(
        eligible = eligible,
        alreadyReviewed = alreadyReviewed,
        canSubmit = canSubmit
    )
}
