package com.example.baltazar.ui.screens.onboarding

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.baltazar.R
import com.example.baltazar.domain.model.OnboardingModel
import dagger.hilt.android.lifecycle.HiltViewModel
import jakarta.inject.Inject
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

@HiltViewModel
class OnboardingViewModel @Inject constructor() : ViewModel() {
    private val _state = MutableStateFlow(
        OnboardingState(
            pages = listOf(
                OnboardingModel(
                    title = R.string.onboarding_title_1,
                    description = R.string.onboarding_description_1,
                    imageSource = R.drawable.ic_launcher_foreground
                ),
                OnboardingModel(
                    title = R.string.onboarding_title_2,
                    description = R.string.onboarding_description_2,
                    imageSource = R.drawable.ic_launcher_foreground
                ),
                OnboardingModel(
                    title = R.string.onboarding_title_3,
                    description = R.string.onboarding_description_3,
                    imageSource = R.drawable.ic_launcher_foreground
                )
            )
        )
    )
    val state: StateFlow<OnboardingState> = _state.asStateFlow()

    fun setOnboardingCompleted() {
        viewModelScope.launch {
            _state.update { it.copy(isCompleted = true) }
        }
    }
}