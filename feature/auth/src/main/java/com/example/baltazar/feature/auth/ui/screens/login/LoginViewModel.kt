package com.example.baltazar.feature.auth.ui.screens.login

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.baltazar.core.core.utils.UiText
import com.example.baltazar.feature.auth.core.extensions.emailError
import com.example.baltazar.feature.auth.core.extensions.passwordError
import com.example.baltazar.feature.auth.core.mapper.toUiText
import com.example.baltazar.feature.auth.domain.repository.IAuthRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers.IO
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class LoginViewModel @Inject constructor(
    private val authRepository: IAuthRepository
) : ViewModel() {

    private val _state = MutableStateFlow(LoginState())
    val state: StateFlow<LoginState> = _state.asStateFlow()

    fun login(email: String, password: String) {
        if (!validate(email, password) || _state.value.isLoading || _state.value.isSuccess) return

        viewModelScope.launch(IO) {
            _state.update {
                it.copy(
                    isLoading = true,
                    generalError = null
                )
            }

            authRepository.login(email, password)
                .onSuccess {
                    _state.update { it.copy(isLoading = false, isSuccess = true) }
                }
                .onFailure { e ->
                    val error = e.message?.let { UiText.DynamicString(it) }
                    _state.update {
                        it.copy(
                            isLoading = false,
                            generalError = error
                        )
                    }
                }
        }
    }

    fun loginWithGoogle(idToken: String) {
        if (_state.value.isLoading || _state.value.isSuccess) return

        viewModelScope.launch(IO) {
            _state.update {
                it.copy(
                    isLoading = true,
                    generalError = null
                )
            }

            authRepository.loginWithGoogle(idToken)
                .onSuccess {
                    _state.update { it.copy(isLoading = false, isSuccess = true) }
                }
                .onFailure { e ->
                    val error = e.message?.let { UiText.DynamicString(it) }
                    _state.update {
                        it.copy(
                            isLoading = false,
                            generalError = error
                        )
                    }
                }
        }
    }

    private fun validate(email: String, password: String): Boolean {
        val emailError = email.emailError()?.toUiText()
        val passwordError = password.passwordError()?.toUiText()

        _state.update {
            it.copy(
                emailError = emailError,
                passwordError = passwordError
            )
        }

        return emailError == null && passwordError == null
    }
}

