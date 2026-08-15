package com.example.baltazar.feature.auth.ui.screens.register

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.baltazar.core.core.constants.CacheKeys
import com.example.baltazar.core.core.enums.Language
import com.example.baltazar.core.core.enums.Region
import com.example.baltazar.core.core.managers.CacheManager
import com.example.baltazar.core.core.utils.SnackbarMessage
import com.example.baltazar.core.core.utils.SnackbarType
import com.example.baltazar.core.core.utils.UiText
import com.example.baltazar.core.domain.repository.IUserRepository
import com.example.baltazar.feature.auth.R
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
    private val userRepository: IUserRepository,
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
        if (!validate(name, email, phone, password) || _state.value.isLoading || _state.value.isSuccess || _state.value.isAuthenticationComplete) return

        viewModelScope.launch(IO) {
            _state.update {
                it.copy(
                    isLoading = true,
                    generalError = null
                )
            }

            authRepository.register(name, email, password, phone, region, language)
                .onSuccess {
                    userRepository.getCurrentUser()
                    _state.update {
                        it.copy(
                            isLoading = false,
                            isSuccess = true,
                            isAuthenticationComplete = true,
                            userMessage = SnackbarMessage(
                                text = UiText.StringResource(R.string.register_success),
                                type = SnackbarType.SUCCESS
                            )
                        )
                    }
                }
                .onFailure { e ->
                    val errorMsg = e.message?.let { UiText.DynamicString(it) } ?: UiText.StringResource(R.string.error_invalid_format)
                    _state.update {
                        it.copy(
                            isLoading = false,
                            generalError = errorMsg,
                            userMessage = SnackbarMessage(text = errorMsg, type = SnackbarType.ERROR)
                        )
                    }
                }
        }
    }

    fun loginWithGoogle(idToken: String) {
        if (_state.value.isLoading || _state.value.isSuccess || _state.value.isAuthenticationComplete) return

        viewModelScope.launch(IO) {
            _state.update { it.copy(isLoading = true, generalError = null) }

            authRepository.loginWithGoogle(idToken)
                .onSuccess {
                    userRepository.getCurrentUser()
                    _state.update {
                        it.copy(
                            isLoading = false,
                            isSuccess = true,
                            isAuthenticationComplete = true,
                            userMessage = SnackbarMessage(
                                text = UiText.StringResource(R.string.register_success),
                                type = SnackbarType.SUCCESS
                            )
                        )
                    }
                }
                .onFailure { e ->
                    val errorMsg = e.message?.let { UiText.DynamicString(it) } ?: UiText.StringResource(R.string.error_invalid_format)
                    _state.update {
                        it.copy(
                            isLoading = false,
                            generalError = errorMsg,
                            userMessage = SnackbarMessage(text = errorMsg, type = SnackbarType.ERROR)
                        )
                    }
                }
        }
    }

    fun onMessageShown() {
        _state.update { it.copy(userMessage = null) }
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
                generalError = UiText.DynamicString(message),
                userMessage = SnackbarMessage(text = UiText.DynamicString(message), type = SnackbarType.ERROR)
            )
        }
    }
}
