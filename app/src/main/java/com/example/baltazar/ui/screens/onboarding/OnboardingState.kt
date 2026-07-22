package com.example.baltazar.ui.screens.onboarding

import com.example.baltazar.domain.model.OnboardingModel

data class OnboardingState(
    val isCompleted: Boolean = false,
    val pages: List<OnboardingModel> = emptyList()
)