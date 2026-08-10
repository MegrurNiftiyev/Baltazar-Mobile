package com.example.baltazar.ui.screens.splash

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.baltazar.core.core.constants.CacheKeys
import com.example.baltazar.core.core.managers.CacheManager
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers.IO
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SplashViewModel @Inject constructor(
    private val cacheManager: CacheManager
) : ViewModel() {
    private val _state = MutableStateFlow(SplashState())
    val state: StateFlow<SplashState> = _state.asStateFlow()


    init {
        checkStatus()
    }

    fun checkStatus() {
        viewModelScope.launch(IO) {
            val isOnboarded =
                cacheManager.getBoolean(CacheKeys.IS_ONBOARDED, false).first()
            val isLoginFinished =
                cacheManager.getBoolean(CacheKeys.IS_LOGIN_FINISHED, false).first()
            _state.update {
                it.copy(
                    isLoading = false,
                    isOnboarded = isOnboarded,
                    isLoginFinished = isLoginFinished
                )
            }
        }
    }

}

