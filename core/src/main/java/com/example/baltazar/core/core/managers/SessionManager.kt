package com.example.baltazar.core.core.managers

import com.example.baltazar.core.core.enums.UserRole
import com.example.baltazar.core.domain.model.User
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.asStateFlow
import javax.inject.Inject
import javax.inject.Singleton

sealed interface AuthEvent {
    data class RequireLogin(val allowReturnToPrevious: Boolean = false) : AuthEvent
}

@Singleton
class SessionManager @Inject constructor() {

    companion object {
        val DEFAULT_GUEST_USER = User(
            id = "guest",
            name = "",
            email = "",
            role = UserRole.Guest,
            phone = null,
            region = null,
            language = "az",
            avatarUrl = null,
            personalInfoCompleted = false,
            driverLicenseCompleted = false,
            passportCompleted = false,
            createdAt = ""
        )
    }

    private val _authEvents = MutableSharedFlow<AuthEvent>(extraBufferCapacity = 1)
    val authEvents: SharedFlow<AuthEvent> = _authEvents.asSharedFlow()

    private val _user = MutableStateFlow<User>(DEFAULT_GUEST_USER)
    val user: StateFlow<User> = _user.asStateFlow()

    private val _isLoadingUser = MutableStateFlow(false)
    val isLoadingUser: StateFlow<Boolean> = _isLoadingUser.asStateFlow()

    fun requireLogin(allowReturnToPrevious: Boolean = false) {
        _authEvents.tryEmit(AuthEvent.RequireLogin(allowReturnToPrevious))
    }

    fun setLoading(isLoading: Boolean) {
        _isLoadingUser.value = isLoading
    }

    fun set(user: User) {
        _user.value = user
        _isLoadingUser.value = false
    }

    fun clear() {
        _user.value = DEFAULT_GUEST_USER
        _isLoadingUser.value = false
    }

    fun update(block: (User) -> User) {
        set(block(_user.value))
    }
}
