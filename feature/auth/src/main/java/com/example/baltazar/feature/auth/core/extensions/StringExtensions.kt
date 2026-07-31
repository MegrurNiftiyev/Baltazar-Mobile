package com.example.baltazar.feature.auth.core.extensions

import com.example.baltazar.feature.auth.core.error.ValidationError

fun String.usernameError(): ValidationError? = when {
    isBlank() -> ValidationError.Blank
    length < 2 -> ValidationError.TooShort
    length > 120 -> ValidationError.TooLong
    else -> null
}

fun String.emailError(): ValidationError? {
    val regex = Regex("^[A-Za-z0-9+_.-]+@[A-Za-z0-9.-]+\\.[A-Za-z]{2,}$")
    return when {
        isBlank() -> ValidationError.Blank
        !matches(regex) -> ValidationError.InvalidFormat
        else -> null
    }
}

fun String.passwordError(): ValidationError? {
    val specialCharRegex = Regex("[^A-Za-z0-9]")
    return when {
        isBlank() -> ValidationError.Blank
        length < 8 -> ValidationError.TooShort
        none { it.isUpperCase() } -> ValidationError.MissingUppercase
        !contains(specialCharRegex) -> ValidationError.MissingSpecialChar
        else -> null
    }
}

fun String.phoneNumberError(): ValidationError? {
    val regex = Regex("^\\+?[0-9]{9,15}$")
    return when {
        isBlank() -> ValidationError.Blank
        !matches(regex) -> ValidationError.InvalidFormat
        else -> null
    }
}
