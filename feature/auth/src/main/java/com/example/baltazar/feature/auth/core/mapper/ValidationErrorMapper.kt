package com.example.baltazar.feature.auth.core.mapper

import androidx.compose.runtime.Composable
import com.example.baltazar.core.core.utils.UiText
import com.example.baltazar.feature.auth.R
import com.example.baltazar.feature.auth.core.error.ValidationError

fun ValidationError.toUiText(): UiText = when (this) {
    ValidationError.Blank -> UiText.StringResource(R.string.error_field_blank)
    ValidationError.InvalidFormat -> UiText.StringResource(R.string.error_invalid_format)
    ValidationError.TooShort -> UiText.StringResource(R.string.error_too_short)
    ValidationError.TooLong -> UiText.StringResource(R.string.error_too_long)
    ValidationError.MissingDigit -> UiText.StringResource(R.string.error_missing_digit)
    ValidationError.MissingUppercase -> UiText.StringResource(R.string.error_missing_uppercase)
    ValidationError.MissingSpecialChar -> UiText.StringResource(R.string.error_missing_special_char)
}

@Composable
fun ValidationError.toMessage(): String = toUiText().asString()
