package com.example.baltazar.core.core.utils

import androidx.compose.material3.SnackbarDuration
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.SnackbarVisuals
import com.example.baltazar.core.core.enums.SnackbarType

data class AppSnackbarVisuals(
    override val message: String,
    val type: SnackbarType = SnackbarType.Success,
    override val actionLabel: String? = null,
    override val withDismissAction: Boolean = false,
    override val duration: SnackbarDuration = SnackbarDuration.Short
) : SnackbarVisuals

object AppSnackbar {
    private var snackbarHostState: SnackbarHostState? = null

    fun bind(hostState: SnackbarHostState) {
        this.snackbarHostState = hostState
    }

    suspend fun show(
        message: String,
        type: SnackbarType = SnackbarType.Success,
        duration: SnackbarDuration = SnackbarDuration.Short,
        actionLabel: String? = null,
        withDismissAction: Boolean = false
    ) {
        snackbarHostState?.showSnackbar(
            AppSnackbarVisuals(
                message = message,
                type = type,
                actionLabel = actionLabel,
                withDismissAction = withDismissAction,
                duration = duration
            )
        )
    }

    suspend fun success(message: String) = show(message, SnackbarType.Success)
    suspend fun error(message: String) = show(message, SnackbarType.Error)
    suspend fun warning(message: String) = show(message, SnackbarType.Warning)
}
