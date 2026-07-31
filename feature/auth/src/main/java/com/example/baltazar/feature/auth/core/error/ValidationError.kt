package com.example.baltazar.feature.auth.core.error

sealed class ValidationError {
    data object Blank : ValidationError()
    data object InvalidFormat : ValidationError()
    data object TooShort : ValidationError()
    data object TooLong : ValidationError()
    data object MissingDigit : ValidationError()
    data object MissingUppercase : ValidationError()
    data object MissingSpecialChar : ValidationError()
}
