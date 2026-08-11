package com.example.baltazar.feature.auth.ui.screens.register

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.baltazar.core.core.constants.CacheKeys
import com.example.baltazar.core.core.enums.Language
import com.example.baltazar.core.core.enums.Region
import com.example.baltazar.core.core.managers.CacheManager
import com.example.baltazar.core.core.utils.UiText
import com.example.baltazar.feature.auth.core.extensions.emailError
import com.example.baltazar.feature.auth.core.extensions.passwordError
import com.example.baltazar.feature.auth.core.extensions.phoneNumberError
import com.example.baltazar.feature.auth.core.extensions.usernameError
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
class RegisterViewModel @Inject constructor(
    private val authRepository: IAuthRepository,
    private val cacheManager: CacheManager
) : ViewModel() {

    private val _state = MutableStateFlow(RegisterState())
    val state: StateFlow<RegisterState> = _state.asStateFlow()

    fun continueAsGuest() {
        viewModelScope.launch(IO) {
            cacheManager.setBoolean(CacheKeys.IS_LOGIN_FINISHED, true)
        }
    }


    fun register(
        name: String,
        email: String,
        phone: String,
        password: String,
        region: Region = Region.AZ,
        language: Language = Language.AZ
    ) {
        if (!validate(name, email, phone, password) || _state.value.isLoading || _state.value.isSuccess) return

        viewModelScope.launch(IO) {
            _state.update {
                it.copy(
                    isLoading = true,
                    generalError = null
                )
            }

            authRepository.register(name, email, password, phone, region, language)
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
            _state.update { it.copy(isLoading = true, generalError = null) }

            authRepository.loginWithGoogle(idToken)
                .onSuccess {
                    _state.update { it.copy(isLoading = false, isSuccess = true) }
                }
                .onFailure { e ->
                    val error = e.message?.let { UiText.DynamicString(it) }
                    _state.update { it.copy(isLoading = false, generalError = error) }
                }
        }
    }

    private fun validate(
        name: String,
        email: String,
        phone: String,
        password: String
    ): Boolean {
        val nameError = name.usernameError()?.toUiText()
        val emailError = email.emailError()?.toUiText()
        val phoneError = phone.phoneNumberError()?.toUiText()
        val passwordError = password.passwordError()?.toUiText()

        _state.update {
            it.copy(
                nameError = nameError,
                emailError = emailError,
                phoneError = phoneError,
                passwordError = passwordError
            )
        }

        return listOf(nameError, emailError, phoneError, passwordError).all { it == null }
    }

    fun setError(message: String) {
        _state.update {
            it.copy(
                isLoading = false,
                generalError = UiText.DynamicString(message)
            )
        }
    }
}

