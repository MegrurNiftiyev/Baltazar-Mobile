package com.example.baltazar.feature.profile.ui.screens.profile

import android.content.Context
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.baltazar.core.core.enums.CardViewMode
import com.example.baltazar.core.core.enums.Language
import com.example.baltazar.core.core.enums.Region
import com.example.baltazar.core.core.extensions.changeLanguage
import com.example.baltazar.core.core.managers.SessionManager
import com.example.baltazar.core.domain.repository.ISettingsRepository
import com.example.baltazar.core.domain.repository.IUserRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ProfileViewModel @Inject constructor(
    private val sessionManager: SessionManager,
    private val settingsRepository: ISettingsRepository,
    private val userRepository: IUserRepository
) : ViewModel() {

    private val _state = MutableStateFlow(ProfileState())
    val state: StateFlow<ProfileState> = _state.asStateFlow()

    init {
        viewModelScope.launch {
            combine(
                sessionManager.user,
                settingsRepository.isDarkMode,
                settingsRepository.language,
                settingsRepository.region,
                settingsRepository.cardViewMode
            ) { user, isDarkMode, language, region, cardViewMode ->
                _state.update {
                    it.copy(
                        user = user,
                        isDarkMode = isDarkMode,
                        selectedLanguage = language,
                        selectedRegion = region,
                        cardViewMode = cardViewMode
                    )
                }
            }.collect {}
        }
    }

    fun changeTheme(enabled: Boolean) {
        viewModelScope.launch(Dispatchers.IO) {
            settingsRepository.setDarkMode(enabled)
        }
    }

    fun openLanguageSheet() {
        _state.update { it.copy(isLanguageSheetOpen = true) }
    }

    fun closeLanguageSheet() {
        _state.update { it.copy(isLanguageSheetOpen = false) }
    }

    fun setLanguage(context: Context, language: Language) {
        viewModelScope.launch(Dispatchers.IO) {
            context.changeLanguage(language)
            settingsRepository.setLanguage(language)
            closeLanguageSheet()

            val currentUser = sessionManager.user.value
            if (currentUser.role != "GUEST") {
                userRepository.updateProfile(
                    name = currentUser.name,
                    language = language.code
                )
            }
        }
    }

    fun openRegionSheet() {
        _state.update { it.copy(isRegionSheetOpen = true) }
    }

    fun closeRegionSheet() {
        _state.update { it.copy(isRegionSheetOpen = false) }
    }

    fun setRegion(region: Region) {
        viewModelScope.launch(Dispatchers.IO) {
            settingsRepository.setRegion(region)
            closeRegionSheet()

            val currentUser = sessionManager.user.value
            if (currentUser.role != "GUEST") {
                userRepository.updateProfile(
                    name = currentUser.name,
                    region = region.code
                )
            }
        }
    }

    fun openCardStyleSheet() {
        _state.update { it.copy(isCardStyleSheetOpen = true) }
    }

    fun closeCardStyleSheet() {
        _state.update { it.copy(isCardStyleSheetOpen = false) }
    }

    fun setCardViewMode(mode: CardViewMode) {
        viewModelScope.launch(Dispatchers.IO) {
            settingsRepository.setCardViewMode(mode)
            closeCardStyleSheet()
        }
    }

    fun openLogoutDialog() {
        _state.update { it.copy(isLogoutDialogOpen = true) }
    }

    fun closeLogoutDialog() {
        _state.update { it.copy(isLogoutDialogOpen = false) }
    }

    fun confirmLogout() {
        viewModelScope.launch(Dispatchers.IO) {
            _state.update { it.copy(isLogoutDialogOpen = false) }
            settingsRepository.logout()
            _state.update { it.copy(isLoggedOut = true) }
        }
    }
}
