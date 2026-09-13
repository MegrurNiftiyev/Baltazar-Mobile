package com.example.baltazar.core.core.enums

enum class UserRole {
    User,
    Admin,
    Guest;

    companion object {
        fun fromString(role: String?): UserRole {
            return when (role?.uppercase()) {
                "USER" -> User
                "ADMIN" -> Admin
                "GUEST" -> Guest
                else -> Guest
            }
        }
    }
}

