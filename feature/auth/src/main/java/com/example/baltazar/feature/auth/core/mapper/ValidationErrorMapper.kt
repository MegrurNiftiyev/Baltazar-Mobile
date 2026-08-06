package com.example.baltazar.feature.auth.core.mapper

import androidx.compose.runtime.Composable
import androidx.compose.ui.res.stringResource
import com.example.baltazar.feature.auth.R
import com.example.baltazar.feature.auth.core.error.ValidationError

@Composable
fun ValidationError.toMessage(): String = when (this) {
    ValidationError.Blank -> stringResource(R.string.error_field_blank)
    ValidationError.InvalidFormat -> stringResource(R.string.error_invalid_format)
    ValidationError.TooShort -> stringResource(R.string.error_too_short)
    ValidationError.TooLong -> stringResource(R.string.error_too_long)
    ValidationError.MissingDigit -> stringResource(R.string.error_missing_digit)
    ValidationError.MissingUppercase -> stringResource(R.string.error_missing_uppercase)
    ValidationError.MissingSpecialChar -> stringResource(R.string.error_missing_special_char)
}