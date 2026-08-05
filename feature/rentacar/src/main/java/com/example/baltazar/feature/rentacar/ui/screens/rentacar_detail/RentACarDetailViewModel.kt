package com.example.baltazar.feature.rentacar.ui.screens.rentacar_detail

import androidx.lifecycle.ViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import javax.inject.Inject

@HiltViewModel
class RentACarDetailViewModel @Inject constructor() : ViewModel() {
    private val _state = MutableStateFlow(RentACarDetailState())
    val state: StateFlow<RentACarDetailState> = _state.asStateFlow()
}
