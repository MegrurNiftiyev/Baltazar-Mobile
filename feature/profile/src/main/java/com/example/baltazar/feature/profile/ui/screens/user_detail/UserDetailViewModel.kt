package com.example.baltazar.feature.profile.ui.screens.user_detail

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.baltazar.core.R
import com.example.baltazar.core.core.utils.SnackbarMessage
import com.example.baltazar.core.core.utils.SnackbarType
import com.example.baltazar.core.core.utils.UiText
import com.example.baltazar.core.domain.repository.IUserRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class UserDetailViewModel @Inject constructor(
    private val userRepository: IUserRepository
) : ViewModel() {

    private val _state = MutableStateFlow(UserDetailState(isLoading = true))
    val state: StateFlow<UserDetailState> = _state.asStateFlow()

    init {
        loadUser()
    }

    private fun loadUser() {
        viewModelScope.launch(Dispatchers.IO) {
            userRepository.getCurrentUser()
                .onSuccess { user ->
                    _state.update {
                        it.copy(
                            name = user.name,
                            phone = user.phone ?: "",
                            isLoading = false
                        )
                    }
                }
                .onFailure { error ->
                    _state.update {
                        it.copy(
                            isLoading = false,
                            userMessage = SnackbarMessage(
                                text = UiText.DynamicString(error.message ?: "Xəta baş verdi"),
                                type = SnackbarType.ERROR
                            )
                        )
                    }
                }
        }
    }

    fun onNameChange(name: String) = _state.update { it.copy(name = name, nameError = null) }
    fun onPhoneChange(phone: String) = _state.update { it.copy(phone = phone, phoneError = null) }
    fun onMessageShown() = _state.update { it.copy(userMessage = null) }

    fun save() {
        if (_state.value.isLoading) return
        val current = _state.value
        var hasError = false
        var nameErr: UiText? = null
        var phoneErr: UiText? = null

        if (current.name.isBlank()) {
            nameErr = UiText.StringResource(R.string.validation_fill_all_fields)
            hasError = true
        }
        if (current.phone.isBlank()) {
            phoneErr = UiText.StringResource(R.string.validation_fill_all_fields)
            hasError = true
        }

        if (hasError) {
            _state.update { it.copy(nameError = nameErr, phoneError = phoneErr) }
            return
        }

        viewModelScope.launch(Dispatchers.IO) {
            _state.update { it.copy(isLoading = true, nameError = null, phoneError = null) }
            userRepository.updateProfile(
                name = current.name.trim(),
                phone = current.phone.trim()
            ).onSuccess {
                _state.update { it.copy(isLoading = false, isSuccess = true) }
            }.onFailure { error ->
                _state.update {
                    it.copy(
                        isLoading = false,
                        userMessage = SnackbarMessage(
                            text = UiText.DynamicString(error.message ?: "Xəta baş verdi"),
                            type = SnackbarType.ERROR
                        )
                    )
                }
            }
        }
    }
}
