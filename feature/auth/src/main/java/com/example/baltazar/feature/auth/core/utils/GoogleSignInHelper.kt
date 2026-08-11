package com.example.baltazar.feature.auth.core.utils

import android.app.Activity
import android.content.Context
import android.content.ContextWrapper
import androidx.credentials.CredentialManager
import androidx.credentials.CustomCredential
import androidx.credentials.GetCredentialRequest
import androidx.credentials.exceptions.GetCredentialCancellationException
import com.example.baltazar.core.BuildConfig
import com.google.android.libraries.identity.googleid.GetGoogleIdOption
import com.google.android.libraries.identity.googleid.GoogleIdTokenCredential
import timber.log.Timber

tailrec fun Context.findActivity(): Activity? = when (this) {
    is Activity -> this
    is ContextWrapper -> baseContext.findActivity()
    else -> null
}

suspend fun launchGoogleSignIn(
    context: Context,
    onSuccess: (idToken: String) -> Unit,
    onError: (errorMessage: String) -> Unit
) {
    val webClientId = BuildConfig.WEB_CLIENT_ID
    if (webClientId.isBlank()) {
        Timber.e("Google Sign-In failed: WEB_CLIENT_ID is empty")
        onError("WEB_CLIENT_ID is missing")
        return
    }

    val activity = context.findActivity()
    val targetContext = activity ?: context

    try {
        val googleIdOption = GetGoogleIdOption.Builder()
            .setFilterByAuthorizedAccounts(false)
            .setServerClientId(webClientId)
            .setAutoSelectEnabled(false)
            .build()

        val request = GetCredentialRequest.Builder()
            .addCredentialOption(googleIdOption)
            .build()

        val credentialManager = CredentialManager.create(targetContext)
        val result = credentialManager.getCredential(context = targetContext, request = request)
        val credential = result.credential

        if (credential is CustomCredential && credential.type == GoogleIdTokenCredential.TYPE_GOOGLE_ID_TOKEN_CREDENTIAL) {
            val googleIdTokenCredential = GoogleIdTokenCredential.createFrom(credential.data)
            onSuccess(googleIdTokenCredential.idToken)
        } else {
            Timber.e("Unsupported credential type: ${credential.type}")
            onError("Unsupported credential type received")
        }
    } catch (e: GetCredentialCancellationException) {
        Timber.d("User cancelled Google sign-in")
    } catch (e: Exception) {
        Timber.e(e, "Google sign-in exception")
        onError(e.message ?: "Google sign-in failed")
    }
}

