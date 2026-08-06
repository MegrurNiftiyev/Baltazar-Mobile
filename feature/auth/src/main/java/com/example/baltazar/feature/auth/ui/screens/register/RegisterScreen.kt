package com.example.baltazar.feature.auth.ui.screens.register

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Email
import androidx.compose.material.icons.filled.Lock
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.Phone
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import com.example.baltazar.core.components.CustomTextField
import com.example.baltazar.core.components.RoundedButton
import com.example.baltazar.core.constants.Paddings
import com.example.baltazar.core.constants.Spaces
import com.example.baltazar.core.navigation.Explore
import com.example.baltazar.feature.auth.core.mapper.toMessage


@Composable
fun RegisterScreen(
    navController: NavHostController,
    viewModel: RegisterViewModel = hiltViewModel()
) {
    val state by viewModel.state.collectAsState()
    var name by remember { mutableStateOf("") }
    var email by remember { mutableStateOf("") }
    var phone by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(Paddings.Medium),
        verticalArrangement = Arrangement.Center
    ) {
        Text("Hesab yarat", style = MaterialTheme.typography.headlineMedium)
        Spacer(Modifier.height(Spaces.Medium))

        CustomTextField(
            value = name,
            onValueChange = { name = it },
            label = "Ad",
            leadingIcon = { Icon(Icons.Default.Person, contentDescription = null) },
            errorText = state.nameError?.toMessage()
        )
        Spacer(Modifier.height(Spaces.Medium))

        CustomTextField(
            value = email,
            onValueChange = { email = it },
            label = "Email",
            keyboardType = KeyboardType.Email,
            leadingIcon = { Icon(Icons.Default.Email, contentDescription = null) },
            errorText = state.emailError?.toMessage()
        )
        Spacer(Modifier.height(Spaces.Medium))

        CustomTextField(
            value = phone,
            onValueChange = { phone = it },
            label = "Telefon",
            keyboardType = KeyboardType.Phone,
            leadingIcon = { Icon(Icons.Default.Phone, contentDescription = null) },
            errorText = state.phoneError?.toMessage()
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
            Text(
                it,
                color = MaterialTheme.colorScheme.error,
                style = MaterialTheme.typography.bodySmall
            )
        }

        Spacer(Modifier.height(Spaces.Large))

        RoundedButton(
            text = "Qeydiyyatdan keç",
            isLoading = state.isLoading,
            onClick = {
                viewModel.register(name, email, phone, password) {
                    navController.navigate(Explore) { popUpTo(0) { inclusive = true } }
                }
            }
        )
    }
}
