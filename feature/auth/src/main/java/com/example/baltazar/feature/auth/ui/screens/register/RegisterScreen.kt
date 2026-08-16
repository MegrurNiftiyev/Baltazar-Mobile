package com.example.baltazar.feature.auth.ui.screens.register

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
import androidx.compose.ui.text.style.TextAlign
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import com.example.baltazar.core.core.components.CustomTextField
import com.example.baltazar.core.core.components.RoundedButton
import com.example.baltazar.core.core.constants.Paddings
import com.example.baltazar.core.core.constants.Spaces
import com.example.baltazar.core.core.enums.CornerShape
import com.example.baltazar.core.core.navigation.Home
import com.example.baltazar.core.core.utils.AppSnackbar
import com.example.baltazar.core.core.utils.SnackbarType
import com.example.baltazar.feature.auth.R
import com.example.baltazar.feature.auth.core.utils.launchGoogleSignIn
import com.example.baltazar.feature.auth.ui.components.SocialIconButton
import compose.icons.TablerIcons
import compose.icons.tablericons.BrandApple
import compose.icons.tablericons.BrandFacebook
import compose.icons.tablericons.BrandGoogle
import compose.icons.tablericons.Lock
import compose.icons.tablericons.Mail
import compose.icons.tablericons.Phone
import compose.icons.tablericons.User
import kotlinx.coroutines.launch

@Composable
fun RegisterScreen(
    navController: NavHostController,
    viewModel: RegisterViewModel = hiltViewModel()
) {
    val context = LocalContext.current
    val coroutineScope = rememberCoroutineScope()
    val state by viewModel.state.collectAsState()
    var name by remember { mutableStateOf("") }
    var email by remember { mutableStateOf("") }
    var phone by remember { mutableStateOf("") }
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
                shape = CornerShape.Circle,
                leadingIcon = { Icon(TablerIcons.User, contentDescription = null) },
                errorText = state.nameError?.asString()
            )
            Spacer(Modifier.height(Spaces.Medium))

            CustomTextField(
                value = email,
                onValueChange = { email = it },
                label = stringResource(R.string.register_email_label),
                placeholder = stringResource(R.string.register_email_placeholder),
                keyboardType = KeyboardType.Email,
                shape = CornerShape.Circle,
                leadingIcon = { Icon(TablerIcons.Mail, contentDescription = null) },
                errorText = state.emailError?.asString()
            )
            Spacer(Modifier.height(Spaces.Medium))

            CustomTextField(
                value = phone,
                onValueChange = { phone = it },
                label = stringResource(R.string.register_phone_label),
                placeholder = stringResource(R.string.register_phone_placeholder),
                keyboardType = KeyboardType.Phone,
                shape = CornerShape.Circle,
                leadingIcon = { Icon(TablerIcons.Phone, contentDescription = null) },
                errorText = state.phoneError?.asString()
            )
            Spacer(Modifier.height(Spaces.Medium))

            CustomTextField(
                value = password,
                onValueChange = { password = it },
                label = stringResource(R.string.register_password_label),
                visualTransformation = PasswordVisualTransformation(),
                shape = CornerShape.Circle,
                leadingIcon = { Icon(TablerIcons.Lock, contentDescription = null) },
                errorText = state.passwordError?.asString()
            )

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
                enabled = isButtonsEnabled,
                isLoading = state.isLoading,
                onClick = {
                    viewModel.register(name, email, phone, password)
                }
            )

            Spacer(Modifier.height(Spaces.Medium))

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

            Spacer(Modifier.height(Spaces.Medium))

            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.Center,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = stringResource(R.string.register_already_have_account),
                    style = MaterialTheme.typography.bodyMedium,
                    color = MaterialTheme.colorScheme.onSurfaceVariant
                )
                TextButton(
                    enabled = isButtonsEnabled,
                    onClick = { navController.popBackStack() }
                ) {
                    Text(
                        text = stringResource(R.string.register_login_now),
                        color = MaterialTheme.colorScheme.primary,
                        style = MaterialTheme.typography.bodyMedium,
                        fontWeight = FontWeight.Bold
                    )
                }
            }
        }

        Spacer(modifier = Modifier.weight(0.65f))
    }
}
