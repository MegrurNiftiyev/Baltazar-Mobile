package com.example.baltazar.core.core.utils

enum class SnackbarType {
    SUCCESS,
    ERROR
}

data class SnackbarMessage(
    val text: UiText,
    val type: SnackbarType = SnackbarType.ERROR
)
