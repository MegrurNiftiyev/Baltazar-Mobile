package com.example.baltazar.feature.auth.ui.screens.register

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.style.TextAlign
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import com.example.baltazar.core.components.CustomTextField
import com.example.baltazar.core.components.RoundedButton
import com.example.baltazar.core.constants.Paddings
import com.example.baltazar.core.constants.Spaces
import com.example.baltazar.core.navigation.Explore
import com.example.baltazar.feature.auth.R
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
            .background(MaterialTheme.colorScheme.background)
            .padding(Paddings.Medium),
        verticalArrangement = Arrangement.Center
    ) {
        Text(
            text = stringResource(R.string.register_title),
            style = MaterialTheme.typography.headlineMedium,
            fontWeight = FontWeight.Bold,
            color = MaterialTheme.colorScheme.primary,
            modifier = Modifier.fillMaxWidth(),
            textAlign = TextAlign.Center
        )

        Spacer(Modifier.height(Spaces.Mini))

        Text(
            text = stringResource(R.string.register_subtitle),
            style = MaterialTheme.typography.bodyMedium,
            color = MaterialTheme.colorScheme.onSurfaceVariant,
            modifier = Modifier.fillMaxWidth(),
            textAlign = TextAlign.Center
        )

        Spacer(Modifier.height(Spaces.Large))

        CustomTextField(
            value = name,
            onValueChange = { name = it },
            label = stringResource(R.string.register_name_label),
            placeholder = stringResource(R.string.register_name_placeholder),
            errorText = state.nameError?.toMessage()
        )
        Spacer(Modifier.height(Spaces.Medium))

        CustomTextField(
            value = email,
            onValueChange = { email = it },
            label = stringResource(R.string.register_email_label),
            placeholder = stringResource(R.string.register_email_placeholder),
            keyboardType = KeyboardType.Email,
            errorText = state.emailError?.toMessage()
        )
        Spacer(Modifier.height(Spaces.Medium))

        CustomTextField(
            value = phone,
            onValueChange = { phone = it },
            label = stringResource(R.string.register_phone_label),
            placeholder = stringResource(R.string.register_phone_placeholder),
            keyboardType = KeyboardType.Phone,
            errorText = state.phoneError?.toMessage()
        )
        Spacer(Modifier.height(Spaces.Medium))

        CustomTextField(
            value = password,
            onValueChange = { password = it },
            label = stringResource(R.string.register_password_label),
            visualTransformation = PasswordVisualTransformation(),
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

        Spacer(Modifier.height(Spaces.Small))

        Text(
            text = stringResource(R.string.register_auto_detect_info),
            style = MaterialTheme.typography.bodySmall,
            color = MaterialTheme.colorScheme.onSurfaceVariant,
            textAlign = TextAlign.Center,
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = Paddings.Small)
        )

        Spacer(Modifier.height(Spaces.Large))

        RoundedButton(
            text = stringResource(R.string.register_button),
            isLoading = state.isLoading,
            onClick = {
                viewModel.register(name, email, phone, password) {
                    navController.navigate(Explore) { popUpTo(0) { inclusive = true } }
                }
            }
        )

        Spacer(Modifier.height(Spaces.Medium))

        TextButton(
            onClick = { navController.popBackStack() },
            modifier = Modifier.fillMaxWidth()
        ) {
            Text(
                stringResource(R.string.register_back_to_login),
                color = MaterialTheme.colorScheme.primary,
                style = MaterialTheme.typography.bodyMedium
            )
        }
    }
}
