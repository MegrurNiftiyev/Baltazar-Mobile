package com.example.baltazar.feature.travel.ui.screens.travels

import androidx.lifecycle.ViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import javax.inject.Inject

@HiltViewModel
class TravelsViewModel @Inject constructor() : ViewModel() {
    private val _state = MutableStateFlow(TravelsState())
    val state: StateFlow<TravelsState> = _state.asStateFlow()
}
