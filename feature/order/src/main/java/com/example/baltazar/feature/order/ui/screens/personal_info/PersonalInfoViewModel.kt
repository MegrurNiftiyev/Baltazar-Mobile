package com.example.baltazar.feature.order.ui.screens.personal_info

import androidx.lifecycle.ViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import javax.inject.Inject

@HiltViewModel
class PersonalInfoViewModel @Inject constructor() : ViewModel() {
    private val _state = MutableStateFlow(PersonalInfoState())
    val state: StateFlow<PersonalInfoState> = _state.asStateFlow()
}
