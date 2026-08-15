package com.example.baltazar.core.core.managers

import com.example.baltazar.core.domain.model.User
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class SessionManager @Inject constructor() {

    companion object {
        val DEFAULT_GUEST_USER = User(
            id = "guest",
            name = "",
            email = "",
            role = "GUEST",
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

    private val _user = MutableStateFlow<User>(DEFAULT_GUEST_USER)
    val user: StateFlow<User> = _user.asStateFlow()

    private val _isLoadingUser = MutableStateFlow(false)
    val isLoadingUser: StateFlow<Boolean> = _isLoadingUser.asStateFlow()

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
