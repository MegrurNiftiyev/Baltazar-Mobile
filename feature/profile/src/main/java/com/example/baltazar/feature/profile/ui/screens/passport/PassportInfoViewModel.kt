package com.example.baltazar.feature.profile.ui.screens.passport

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
class PassportInfoViewModel @Inject constructor(
    private val userRepository: IUserRepository
) : ViewModel() {

    private val _state = MutableStateFlow(PassportInfoState())
    val state: StateFlow<PassportInfoState> = _state.asStateFlow()

    fun onPassportNumberChange(passportNumber: String) = _state.update { it.copy(passportNumber = passportNumber, passportNumberError = null) }
    fun onExpiryDateChange(expiryDate: String) = _state.update { it.copy(expiryDate = expiryDate, expiryDateError = null) }
    fun onMessageShown() = _state.update { it.copy(userMessage = null) }

    fun save() {
        if (_state.value.isLoading) return
        val current = _state.value
        var hasError = false
        var pNumErr: UiText? = null
        var expErr: UiText? = null

        if (current.passportNumber.isBlank()) {
            pNumErr = UiText.StringResource(R.string.validation_fill_all_fields)
            hasError = true
        }
        if (current.expiryDate.isBlank()) {
            expErr = UiText.StringResource(R.string.validation_fill_all_fields)
            hasError = true
        }

        if (hasError) {
            _state.update { it.copy(passportNumberError = pNumErr, expiryDateError = expErr) }
            return
        }

        viewModelScope.launch(Dispatchers.IO) {
            _state.update { it.copy(isLoading = true, passportNumberError = null, expiryDateError = null) }
            userRepository.updatePassport(
                passportNumber = current.passportNumber.trim(),
                expiryDate = current.expiryDate.trim()
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
