package com.example.baltazar.feature.profile.ui.screens.personal_info

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
class PersonalInfoViewModel @Inject constructor(
    private val userRepository: IUserRepository
) : ViewModel() {

    private val _state = MutableStateFlow(PersonalInfoState())
    val state: StateFlow<PersonalInfoState> = _state.asStateFlow()

    fun onDateOfBirthChange(dateOfBirth: String) = _state.update { it.copy(dateOfBirth = dateOfBirth, dateOfBirthError = null) }
    fun onAddressChange(address: String) = _state.update { it.copy(address = address, addressError = null) }
    fun onIdNumberChange(idNumber: String) = _state.update { it.copy(idNumber = idNumber, idNumberError = null) }
    fun onMessageShown() = _state.update { it.copy(userMessage = null) }

    fun save() {
        if (_state.value.isLoading) return
        val current = _state.value
        var hasError = false
        var dobErr: UiText? = null
        var addrErr: UiText? = null
        var idErr: UiText? = null

        if (current.dateOfBirth.isBlank()) {
            dobErr = UiText.StringResource(R.string.validation_fill_all_fields)
            hasError = true
        }
        if (current.address.isBlank()) {
            addrErr = UiText.StringResource(R.string.validation_fill_all_fields)
            hasError = true
        }
        if (current.idNumber.isBlank()) {
            idErr = UiText.StringResource(R.string.validation_fill_all_fields)
            hasError = true
        }

        if (hasError) {
            _state.update {
                it.copy(
                    dateOfBirthError = dobErr,
                    addressError = addrErr,
                    idNumberError = idErr
                )
            }
            return
        }

        viewModelScope.launch(Dispatchers.IO) {
            _state.update { it.copy(isLoading = true, dateOfBirthError = null, addressError = null, idNumberError = null) }
            userRepository.updatePersonalInfo(
                dateOfBirth = current.dateOfBirth.trim(),
                address = current.address.trim(),
                idNumber = current.idNumber.trim()
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
