package com.example.baltazar.feature.food.ui.screens.food_company

import androidx.lifecycle.ViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import javax.inject.Inject

@HiltViewModel
class FoodCompanyViewModel @Inject constructor() : ViewModel() {
    private val _state = MutableStateFlow(FoodCompanyState())
    val state: StateFlow<FoodCompanyState> = _state.asStateFlow()
}
