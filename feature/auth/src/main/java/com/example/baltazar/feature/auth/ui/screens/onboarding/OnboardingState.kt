package com.example.baltazar.feature.auth.ui.screens.onboarding

import com.example.baltazar.feature.auth.domain.model.OnboardingModel


data class OnboardingState(
    val isCompleted: Boolean = false,
    val pages: List<OnboardingModel> = emptyList()
)