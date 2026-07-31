package com.example.baltazar.feature.auth.ui.screens.login

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Email
import androidx.compose.material.icons.filled.Lock
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import com.example.baltazar.core.components.CustomTextField
import com.example.baltazar.core.components.RoundedButton
import com.example.baltazar.core.constants.Paddings
import com.example.baltazar.core.constants.Spaces
import com.example.baltazar.core.navigation.Explore
import com.example.baltazar.core.navigation.Register
import com.example.baltazar.feature.auth.R
import com.example.baltazar.feature.auth.core.error.ValidationError

@Composable
private fun ValidationError.toMessage(): String = when (this) {
    ValidationError.Blank -> stringResource(R.string.error_field_blank)
    ValidationError.InvalidFormat -> stringResource(R.string.error_invalid_format)
    ValidationError.TooShort -> stringResource(R.string.error_too_short)
    ValidationError.TooLong -> stringResource(R.string.error_too_long)
    ValidationError.MissingDigit -> stringResource(R.string.error_missing_digit)
    ValidationError.MissingUppercase -> stringResource(R.string.error_missing_uppercase)
    ValidationError.MissingSpecialChar -> stringResource(R.string.error_missing_special_char)
}

@Composable
fun LoginScreen(
    navController: NavHostController,
    viewModel: LoginViewModel = hiltViewModel()
) {
    val state by viewModel.state.collectAsState()
    var email by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }

    Column(
        modifier = Modifier.fillMaxSize().padding(Paddings.Medium),
        verticalArrangement = Arrangement.Center
    ) {
        Text("Xoş gəldin", style = MaterialTheme.typography.headlineMedium)
        Spacer(Modifier.height(Spaces.Medium))

        CustomTextField(
            value = email,
            onValueChange = { email = it },
            label = "Email",
            placeholder = "email@example.com",
            keyboardType = KeyboardType.Email,
            leadingIcon = { Icon(Icons.Default.Email, contentDescription = null) },
            errorText = state.emailError?.toMessage()
        )
        Spacer(Modifier.height(Spaces.Medium))

        CustomTextField(
            value = password,
            onValueChange = { password = it },
            label = "Şifrə",
            visualTransformation = PasswordVisualTransformation(),
            leadingIcon = { Icon(Icons.Default.Lock, contentDescription = null) },
            errorText = state.passwordError?.toMessage()
        )

        state.generalError?.let {
            Spacer(Modifier.height(Spaces.Mini))
            Text(it, color = MaterialTheme.colorScheme.error, style = MaterialTheme.typography.bodySmall)
        }

        Spacer(Modifier.height(Spaces.Large))

        RoundedButton(
            text = "Daxil ol",
            isLoading = state.isLoading,
            onClick = {
                viewModel.login(email, password) {
                    navController.navigate(Explore) { popUpTo(0) { inclusive = true } }
                }
            }
        )
        Spacer(Modifier.height(Spaces.Medium))

        Row(Modifier.fillMaxWidth(), Arrangement.Center, Alignment.CenterVertically) {
            Text("Hesabın yoxdur?", style = MaterialTheme.typography.bodyMedium)
            TextButton(onClick = { navController.navigate(Register) }) { Text("Qeydiyyatdan keç") }
        }
    }
}
