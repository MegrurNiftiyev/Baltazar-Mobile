package com.example.baltazar.ui.screens.home

import androidx.lifecycle.ViewModel
import com.example.baltazar.core.core.managers.SessionManager
import com.example.baltazar.core.domain.model.User
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.StateFlow
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    sessionManager: SessionManager
) : ViewModel() {
    val user: StateFlow<User> = sessionManager.user
}
