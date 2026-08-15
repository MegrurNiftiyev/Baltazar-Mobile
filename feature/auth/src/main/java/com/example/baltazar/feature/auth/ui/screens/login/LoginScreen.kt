package com.example.baltazar.feature.auth.ui.screens.login

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import com.example.baltazar.core.core.components.CustomTextField
import com.example.baltazar.core.core.components.RoundedButton
import com.example.baltazar.core.core.constants.Paddings
import com.example.baltazar.core.core.constants.Spaces
import com.example.baltazar.core.core.enums.CornerShape
import com.example.baltazar.core.core.navigation.Home
import com.example.baltazar.core.core.navigation.Register
import com.example.baltazar.core.core.utils.AppSnackbar
import com.example.baltazar.core.core.utils.SnackbarType
import com.example.baltazar.feature.auth.R
import com.example.baltazar.feature.auth.core.utils.launchGoogleSignIn
import com.example.baltazar.feature.auth.ui.components.HorizontalDividerLine
import com.example.baltazar.feature.auth.ui.components.SocialIconButton
import compose.icons.TablerIcons
import compose.icons.tablericons.BrandApple
import compose.icons.tablericons.BrandFacebook
import compose.icons.tablericons.BrandGoogle
import compose.icons.tablericons.Lock
import compose.icons.tablericons.Mail
import compose.icons.tablericons.User
import kotlinx.coroutines.launch

@Composable
fun LoginScreen(
    navController: NavHostController,
    viewModel: LoginViewModel = hiltViewModel()
) {
    val context = LocalContext.current
    val coroutineScope = rememberCoroutineScope()
    val state by viewModel.state.collectAsState()
    var email by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }

    val isButtonsEnabled = !state.isLoading && !state.isAuthenticationComplete && !state.isSuccess

    LaunchedEffect(state.isSuccess) {
        if (state.isSuccess) {
            kotlinx.coroutines.delay(1000L)
            navController.navigate(Home()) { popUpTo(0) { inclusive = true } }
        }
    }

    LaunchedEffect(state.userMessage) {
        state.userMessage?.let { userMsg ->
            val text = userMsg.text.asString(context)
            when (userMsg.type) {
                SnackbarType.SUCCESS -> AppSnackbar.success(text)
                SnackbarType.ERROR -> AppSnackbar.error(text)
            }
            viewModel.onMessageShown()
        }
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.background)
            .padding(Paddings.Medium),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Spacer(modifier = Modifier.weight(0.35f))

        Column(
            modifier = Modifier.fillMaxWidth(),
            horizontalAlignment = Alignment.CenterHorizontally
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

            CustomTextField(
                value = email,
                onValueChange = { email = it },
                label = stringResource(R.string.login_email_label),
                placeholder = stringResource(R.string.login_email_placeholder),
                keyboardType = KeyboardType.Email,
                shape = CornerShape.Circle,
                leadingIcon = { Icon(TablerIcons.Mail, contentDescription = null) },
                errorText = state.emailError?.asString()
            )
            Spacer(Modifier.height(Spaces.Medium))

            CustomTextField(
                value = password,
                onValueChange = { password = it },
                label = stringResource(R.string.login_password_label),
                placeholder = stringResource(R.string.login_password_placeholder),
                visualTransformation = PasswordVisualTransformation(),
                shape = CornerShape.Circle,
                leadingIcon = { Icon(TablerIcons.Lock, contentDescription = null) },
                errorText = state.passwordError?.asString()
            )

            Spacer(Modifier.height(Spaces.Large))

            RoundedButton(
                text = stringResource(R.string.login_button),
                contentColor = MaterialTheme.colorScheme.background,
                enabled = isButtonsEnabled,
                isLoading = state.isLoading,
                onClick = {
                    viewModel.login(email, password)
                }
            )

            Spacer(Modifier.height(Spaces.Large))

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

            Spacer(Modifier.height(Spaces.Large))

            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(Spaces.Medium, Alignment.CenterHorizontally),
                verticalAlignment = Alignment.CenterVertically
            ) {
                SocialIconButton(
                    icon = TablerIcons.BrandGoogle,
                    contentDescription = "Google Sign In",
                    enabled = isButtonsEnabled,
                    onClick = {
                        coroutineScope.launch {
                            launchGoogleSignIn(
                                context = context,
                                onSuccess = { idToken ->
                                    viewModel.loginWithGoogle(idToken)
                                },
                                onError = { error ->
                                    viewModel.setError(error)
                                }
                            )
                        }
                    }
                )

                SocialIconButton(
                    icon = TablerIcons.BrandApple,
                    contentDescription = "Apple Sign In",
                    enabled = isButtonsEnabled,
                    onClick = {
                        coroutineScope.launch {
                            launchGoogleSignIn(
                                context = context,
                                onSuccess = { idToken ->
                                    viewModel.loginWithGoogle(idToken)
                                },
                                onError = { error ->
                                    viewModel.setError(error)
                                }
                            )
                        }
                    }
                )

                SocialIconButton(
                    icon = TablerIcons.BrandFacebook,
                    contentDescription = "Facebook Sign In",
                    enabled = isButtonsEnabled,
                    onClick = {
                        coroutineScope.launch {
                            launchGoogleSignIn(
                                context = context,
                                onSuccess = { idToken ->
                                    viewModel.loginWithGoogle(idToken)
                                },
                                onError = { error ->
                                    viewModel.setError(error)
                                }
                            )
                        }
                    }
                )

                SocialIconButton(
                    icon = TablerIcons.User,
                    contentDescription = "Continue as Guest",
                    enabled = isButtonsEnabled,
                    onClick = {
                        viewModel.continueAsGuest()
                        navController.navigate(Home()) {
                            popUpTo(0) { inclusive = true }
                        }
                    }
                )
            }

            Spacer(Modifier.height(Spaces.Large))

            Row(Modifier.fillMaxWidth(), Arrangement.Center, Alignment.CenterVertically) {
                Text(
                    stringResource(R.string.login_no_account),
                    style = MaterialTheme.typography.bodyMedium
                )
                TextButton(
                    enabled = isButtonsEnabled,
                    onClick = { navController.navigate(Register) }
                ) {
                    Text(
                        stringResource(R.string.login_register_now),
                        color = MaterialTheme.colorScheme.primary,
                        style = MaterialTheme.typography.bodyMedium,
                        fontWeight = FontWeight.SemiBold
                    )
                }
            }
        }

        Spacer(modifier = Modifier.weight(0.65f))
    }
}
