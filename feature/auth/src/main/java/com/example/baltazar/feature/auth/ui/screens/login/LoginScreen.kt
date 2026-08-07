package com.example.baltazar.feature.auth.ui.screens.login

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Email
import androidx.compose.material.icons.filled.Lock
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
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
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import com.example.baltazar.core.components.CustomTextField
import com.example.baltazar.core.components.RoundedButton
import com.example.baltazar.core.constants.Paddings
import com.example.baltazar.core.constants.Spaces
import com.example.baltazar.core.navigation.Explore
import com.example.baltazar.core.navigation.Register
import com.example.baltazar.feature.auth.R
import com.example.baltazar.feature.auth.core.mapper.toMessage


@Composable
fun LoginScreen(
    navController: NavHostController,
    viewModel: LoginViewModel = hiltViewModel()
) {
    val state by viewModel.state.collectAsState()
    var email by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.background)
            .padding(Paddings.Medium),
        verticalArrangement = Arrangement.Center
    ) {
        Text(
            text = stringResource(R.string.login_title),
            style = MaterialTheme.typography.headlineMedium,
            fontWeight = FontWeight.Bold,
            color = MaterialTheme.colorScheme.primary,
            modifier = Modifier
                .fillMaxWidth()
                .padding(bottom = Spaces.Large),
            textAlign = androidx.compose.ui.text.style.TextAlign.Center
        )

        Card(
            modifier = Modifier.fillMaxWidth(),
            shape = RoundedCornerShape(24.dp),
            colors = CardDefaults.cardColors(
                containerColor = MaterialTheme.colorScheme.surface
            ),
            elevation = CardDefaults.cardElevation(defaultElevation = 0.dp)
        ) {
            Column(
                modifier = Modifier.padding(Paddings.Large)
            ) {
                CustomTextField(
                    value = email,
                    onValueChange = { email = it },
                    label = stringResource(R.string.login_email_label),
                    placeholder = stringResource(R.string.login_email_placeholder),
                    keyboardType = KeyboardType.Email,
                    leadingIcon = { Icon(Icons.Default.Email, contentDescription = null) },
                    errorText = state.emailError?.toMessage()
                )
                Spacer(Modifier.height(Spaces.Medium))

                CustomTextField(
                    value = password,
                    onValueChange = { password = it },
                    label = stringResource(R.string.login_password_label),
                    placeholder = stringResource(R.string.login_password_placeholder),
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

                Spacer(Modifier.height(Spaces.Small))

                TextButton(
                    onClick = { /* TODO: forgot password */ },
                    modifier = Modifier.align(Alignment.End)
                ) {
                    Text(
                        stringResource(R.string.login_forgot_password),
                        style = MaterialTheme.typography.bodySmall
                    )
                }

                Spacer(Modifier.height(Spaces.Small))

                RoundedButton(
                    text = stringResource(R.string.login_button),
                    contentColor = MaterialTheme.colorScheme.background,

                    isLoading = state.isLoading,
                    onClick = {
                        viewModel.login(email, password) {
                            navController.navigate(Explore) { popUpTo(0) { inclusive = true } }
                        }
                    }
                )

                Spacer(Modifier.height(Spaces.Medium))

                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    modifier = Modifier.fillMaxWidth()
                ) {
                    HorizontalDividerLine()
                    Text(
                        text = stringResource(R.string.login_or),
                        style = MaterialTheme.typography.bodySmall,
                        color = MaterialTheme.colorScheme.onSurfaceVariant,
                        modifier = Modifier.padding(horizontal = Paddings.Small)
                    )
                    HorizontalDividerLine()
                }

                Spacer(Modifier.height(Spaces.Medium))

                RoundedButton(
                    text = stringResource(R.string.login_google),
                    onClick = { /* TODO: google sign-in */ },
                )
            }
        }

        Spacer(Modifier.height(Spaces.Medium))

        Row(Modifier.fillMaxWidth(), Arrangement.Center, Alignment.CenterVertically) {
            Text(
                stringResource(R.string.login_no_account),
                style = MaterialTheme.typography.bodyMedium
            )
            TextButton(onClick = { navController.navigate(Register) }) {
                Text(
                    stringResource(R.string.login_register_now),
                    color = MaterialTheme.colorScheme.primary
                )
            }
        }

        TextButton(
            onClick = { navController.navigate(Explore) },
            modifier = Modifier.fillMaxWidth()
        ) {
            Text(
                stringResource(R.string.login_guest),
                color = MaterialTheme.colorScheme.onSurfaceVariant,
                style = MaterialTheme.typography.bodyMedium
            )
        }
    }
}

@Composable
private fun androidx.compose.foundation.layout.RowScope.HorizontalDividerLine() {
    Box(
        modifier = Modifier
            .weight(1f)
            .height(1.dp)
            .background(MaterialTheme.colorScheme.outlineVariant)
    )
}