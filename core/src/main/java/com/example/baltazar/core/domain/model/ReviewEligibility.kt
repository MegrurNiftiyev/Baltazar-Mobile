package com.example.baltazar.core.domain.model

data class ReviewEligibility(
    val eligible: Boolean = false,
    val alreadyReviewed: Boolean = false,
    val canSubmit: Boolean = false
)
