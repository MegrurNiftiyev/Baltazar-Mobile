package com.example.baltazar.feature.order.ui.screens.confirm

import androidx.lifecycle.ViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import javax.inject.Inject

@HiltViewModel
class ConfirmViewModel @Inject constructor() : ViewModel() {
    private val _state = MutableStateFlow(ConfirmState())
    val state: StateFlow<ConfirmState> = _state.asStateFlow()
}
