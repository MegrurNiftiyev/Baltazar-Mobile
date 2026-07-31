package com.example.baltazar.feature.auth.ui.screens.register

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.baltazar.feature.auth.core.extensions.emailError
import com.example.baltazar.feature.auth.core.extensions.passwordError
import com.example.baltazar.feature.auth.core.extensions.phoneNumberError
import com.example.baltazar.feature.auth.core.extensions.usernameError
import com.example.baltazar.feature.auth.domain.repository.IAuthRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class RegisterViewModel @Inject constructor(
    private val IAuthRepository: IAuthRepository
) : ViewModel() {

    private val _state = MutableStateFlow(RegisterState())
    val state: StateFlow<RegisterState> = _state

    fun register(
        name: String,
        email: String,
        phone: String,
        password: String,
        onSuccess: () -> Unit
    ) {
        val nameError = name.usernameError()
        val emailError = email.emailError()
        val phoneError = phone.phoneNumberError()
        val passwordError = password.passwordError()

        if (nameError != null || emailError != null || phoneError != null || passwordError != null) {
            _state.update {
                it.copy(
                    nameError = nameError,
                    emailError = emailError,
                    phoneError = phoneError,
                    passwordError = passwordError
                )
            }
            return
        }

        _state.update {
            it.copy(
                isLoading = true,
                nameError = null,
                emailError = null,
                phoneError = null,
                passwordError = null,
                generalError = null
            )
        }
        viewModelScope.launch {
            IAuthRepository.register(name, email, password, phone)
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
