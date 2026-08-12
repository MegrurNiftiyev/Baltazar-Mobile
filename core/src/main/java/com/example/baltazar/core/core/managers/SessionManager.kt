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
        const val DEFAULT_GUEST_AVATAR = "https://images.unsplash.com/photo-1535713875002-d1d0cf377fde?auto=format&fit=crop&w=200&q=80"
        const val DEFAULT_GUEST_NAME = "Qonaq İstifadəçi"

        val DEFAULT_GUEST_USER = User(
            id = "guest",
            name = DEFAULT_GUEST_NAME,
            email = "",
            role = "GUEST",
            phone = null,
            region = null,
            language = "az",
            avatarUrl = DEFAULT_GUEST_AVATAR,
            personalInfo = false,
            driverLicense = false,
            passport = false,
            createdAt = ""
        )
    }

    private val _user = MutableStateFlow<User>(DEFAULT_GUEST_USER)
    val user: StateFlow<User> = _user.asStateFlow()

    fun set(user: User) {
        val name = user.name.ifBlank { DEFAULT_GUEST_NAME }
        val avatarUrl = if (user.avatarUrl.isNullOrBlank()) DEFAULT_GUEST_AVATAR else user.avatarUrl
        _user.value = user.copy(name = name, avatarUrl = avatarUrl)
    }

    fun clear() {
        _user.value = DEFAULT_GUEST_USER
    }

    fun update(block: (User) -> User) {
        set(block(_user.value))
    }
}
