package com.example.baltazar.feature.order.ui.screens.address

import androidx.lifecycle.ViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import javax.inject.Inject

@HiltViewModel
class AddressViewModel @Inject constructor() : ViewModel() {
    private val _state = MutableStateFlow(AddressState())
    val state: StateFlow<AddressState> = _state.asStateFlow()
}
