package com.example.baltazar.feature.order.ui.screens.passport_info

import androidx.lifecycle.ViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import javax.inject.Inject

@HiltViewModel
class PassportInfoViewModel @Inject constructor() : ViewModel() {
    private val _state = MutableStateFlow(PassportInfoState())
    val state: StateFlow<PassportInfoState> = _state.asStateFlow()
}
