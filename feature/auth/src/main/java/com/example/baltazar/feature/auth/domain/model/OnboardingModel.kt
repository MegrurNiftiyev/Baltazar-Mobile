package com.example.baltazar.feature.auth.domain.model

import androidx.annotation.DrawableRes

data class OnboardingModel(
    val title: Int,
    val description: Int,
    @DrawableRes val imageSource: Int
)