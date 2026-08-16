package com.example.baltazar.feature.order.ui.screens.address_selection

import androidx.lifecycle.ViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import javax.inject.Inject

@HiltViewModel
class AddressSelectionViewModel @Inject constructor() : ViewModel() {
    private val _state = MutableStateFlow(AddressSelectionState())
    val state: StateFlow<AddressSelectionState> = _state.asStateFlow()
}
