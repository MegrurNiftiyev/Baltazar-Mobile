package com.example.baltazar.feature.auth.ui.screens.onboarding

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.baltazar.core.core.constants.CacheKeys
import com.example.baltazar.core.core.managers.CacheManager
import dagger.hilt.android.lifecycle.HiltViewModel
import jakarta.inject.Inject
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

@HiltViewModel
class OnboardingViewModel @Inject constructor(
    private val cacheManger: CacheManager
) : ViewModel() {
    private val _state = MutableStateFlow(
        OnboardingState()
    )
    val state: StateFlow<OnboardingState> = _state.asStateFlow()

    fun setOnboardingCompleted() {
        viewModelScope.launch {
            cacheManger.setBoolean(CacheKeys.IS_ONBOARDED, true)
            _state.update { it.copy(isCompleted = true) }
        }
    }
}
