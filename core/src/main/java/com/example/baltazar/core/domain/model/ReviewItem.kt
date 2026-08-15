package com.example.baltazar.core.domain.model

data class ReviewItem(
    val id: String,
    val userId: String,
    val userName: String,
    val userAvatar: String? = null,
    val targetType: String,
    val targetId: String,
    val rating: Double,
    val comment: String,
    val createdAt: String
)

data class ReviewEligibility(
    val eligible: Boolean = false,
    val alreadyReviewed: Boolean = false,
    val canSubmit: Boolean = false
)
