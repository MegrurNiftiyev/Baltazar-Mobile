package com.example.baltazar.feature.auth.ui.screens.login

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.baltazar.feature.auth.core.extensions.emailError
import com.example.baltazar.feature.auth.core.extensions.passwordError
import com.example.baltazar.feature.auth.domain.repository.IAuthRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class LoginViewModel @Inject constructor(
    private val IAuthRepository: IAuthRepository
) : ViewModel() {

    private val _state = MutableStateFlow(LoginState())
    val state: StateFlow<LoginState> = _state

    fun login(email: String, password: String, onSuccess: () -> Unit) {
        val emailError = email.emailError()
        val passwordError = password.passwordError()

        if (emailError != null || passwordError != null) {
            _state.update { it.copy(emailError = emailError, passwordError = passwordError) }
            return
        }

        _state.update {
            it.copy(
                isLoading = true,
                emailError = null,
                passwordError = null,
                generalError = null
            )
        }
        viewModelScope.launch {
            IAuthRepository.login(email, password)
                .onSuccess {
                    _state.update { it.copy(isLoading = false) }
                    onSuccess()
                }
                .onFailure { e ->
                    _state.update {
                        it.copy(
                            isLoading = false,
                            generalError = e.message ?: "Xəta baş verdi"
                        )
                    }
                }
        }
    }
}
