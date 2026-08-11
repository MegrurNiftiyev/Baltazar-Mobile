package com.example.baltazar.feature.auth.ui.screens.auth_selection

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.baltazar.core.core.constants.CacheKeys
import com.example.baltazar.core.core.managers.CacheManager
import dagger.hilt.android.lifecycle.HiltViewModel
import jakarta.inject.Inject
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

@HiltViewModel
class AuthSelectionViewModel @Inject constructor(
    private val cacheManager: CacheManager
) : ViewModel() {
    private val _state = MutableStateFlow(AuthSelectionState())
    val state: StateFlow<AuthSelectionState> = _state.asStateFlow()


    fun continueAsGuest() {
        viewModelScope.launch {
            cacheManager.setBoolean(CacheKeys.IS_LOGIN_FINISHED, true)

        }
    }
}

