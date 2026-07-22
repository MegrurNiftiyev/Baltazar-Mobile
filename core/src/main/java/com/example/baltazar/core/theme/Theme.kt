package com.example.baltazar.core.theme

import android.os.Build
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.dynamicDarkColorScheme
import androidx.compose.material3.dynamicLightColorScheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.platform.LocalContext

private val LightColorScheme = lightColorScheme(
    primary = _root_ide_package_.com.example.baltazar.core.theme.PrimaryLight,
    onPrimary = _root_ide_package_.com.example.baltazar.core.theme.OnPrimaryLight,
    primaryContainer = _root_ide_package_.com.example.baltazar.core.theme.PrimaryContainerLight,
    onPrimaryContainer = _root_ide_package_.com.example.baltazar.core.theme.OnPrimaryContainerLight,
    inversePrimary = _root_ide_package_.com.example.baltazar.core.theme.InversePrimaryLight,
    secondary = _root_ide_package_.com.example.baltazar.core.theme.SecondaryLight,
    onSecondary = _root_ide_package_.com.example.baltazar.core.theme.OnSecondaryLight,
    secondaryContainer = _root_ide_package_.com.example.baltazar.core.theme.SecondaryContainerLight,
    onSecondaryContainer = _root_ide_package_.com.example.baltazar.core.theme.OnSecondaryContainerLight,
    tertiary = _root_ide_package_.com.example.baltazar.core.theme.TertiaryLight,
    onTertiary = _root_ide_package_.com.example.baltazar.core.theme.OnTertiaryLight,
    tertiaryContainer = _root_ide_package_.com.example.baltazar.core.theme.TertiaryContainerLight,
    onTertiaryContainer = _root_ide_package_.com.example.baltazar.core.theme.OnTertiaryContainerLight,
    background = _root_ide_package_.com.example.baltazar.core.theme.BackgroundLight,
    onBackground = _root_ide_package_.com.example.baltazar.core.theme.OnBackgroundLight,
    surface = _root_ide_package_.com.example.baltazar.core.theme.SurfaceLight,
    onSurface = _root_ide_package_.com.example.baltazar.core.theme.OnSurfaceLight,
    surfaceVariant = _root_ide_package_.com.example.baltazar.core.theme.SurfaceVariantLight,
    onSurfaceVariant = _root_ide_package_.com.example.baltazar.core.theme.OnSurfaceVariantLight,
    surfaceTint = _root_ide_package_.com.example.baltazar.core.theme.PrimaryLight,
    inverseSurface = _root_ide_package_.com.example.baltazar.core.theme.InverseSurfaceLight,
    inverseOnSurface = _root_ide_package_.com.example.baltazar.core.theme.InverseOnSurfaceLight,
    error = _root_ide_package_.com.example.baltazar.core.theme.ErrorLight,
    onError = _root_ide_package_.com.example.baltazar.core.theme.OnErrorLight,
    errorContainer = _root_ide_package_.com.example.baltazar.core.theme.ErrorContainerLight,
    onErrorContainer = _root_ide_package_.com.example.baltazar.core.theme.OnErrorContainerLight,
    outline = _root_ide_package_.com.example.baltazar.core.theme.OutlineLight,
    outlineVariant = _root_ide_package_.com.example.baltazar.core.theme.OutlineVariantLight,
    scrim = _root_ide_package_.com.example.baltazar.core.theme.ScrimLight,
    surfaceBright = _root_ide_package_.com.example.baltazar.core.theme.SurfaceBrightLight,
    surfaceContainer = _root_ide_package_.com.example.baltazar.core.theme.SurfaceContainerLight,
    surfaceContainerHigh = _root_ide_package_.com.example.baltazar.core.theme.SurfaceContainerHighLight,
    surfaceContainerHighest = _root_ide_package_.com.example.baltazar.core.theme.SurfaceContainerHighestLight,
    surfaceContainerLow = _root_ide_package_.com.example.baltazar.core.theme.SurfaceContainerLowLight,
    surfaceContainerLowest = _root_ide_package_.com.example.baltazar.core.theme.SurfaceContainerLowestLight,
    surfaceDim = _root_ide_package_.com.example.baltazar.core.theme.SurfaceDimLight
)

private val DarkColorScheme = darkColorScheme(
    primary = _root_ide_package_.com.example.baltazar.core.theme.PrimaryDark,
    onPrimary = _root_ide_package_.com.example.baltazar.core.theme.OnPrimaryDark,
    primaryContainer = _root_ide_package_.com.example.baltazar.core.theme.PrimaryContainerDark,
    onPrimaryContainer = _root_ide_package_.com.example.baltazar.core.theme.OnPrimaryContainerDark,
    inversePrimary = _root_ide_package_.com.example.baltazar.core.theme.InversePrimaryDark,
    secondary = _root_ide_package_.com.example.baltazar.core.theme.SecondaryDark,
    onSecondary = _root_ide_package_.com.example.baltazar.core.theme.OnSecondaryDark,
    secondaryContainer = _root_ide_package_.com.example.baltazar.core.theme.SecondaryContainerDark,
    onSecondaryContainer = _root_ide_package_.com.example.baltazar.core.theme.OnSecondaryContainerDark,
    tertiary = _root_ide_package_.com.example.baltazar.core.theme.TertiaryDark,
    onTertiary = _root_ide_package_.com.example.baltazar.core.theme.OnTertiaryDark,
    tertiaryContainer = _root_ide_package_.com.example.baltazar.core.theme.TertiaryContainerDark,
    onTertiaryContainer = _root_ide_package_.com.example.baltazar.core.theme.OnTertiaryContainerDark,
    background = _root_ide_package_.com.example.baltazar.core.theme.BackgroundDark,
    onBackground = _root_ide_package_.com.example.baltazar.core.theme.OnBackgroundDark,
    surface = _root_ide_package_.com.example.baltazar.core.theme.SurfaceDark,
    onSurface = _root_ide_package_.com.example.baltazar.core.theme.OnSurfaceDark,
    surfaceVariant = _root_ide_package_.com.example.baltazar.core.theme.SurfaceVariantDark,
    onSurfaceVariant = _root_ide_package_.com.example.baltazar.core.theme.OnSurfaceVariantDark,
    surfaceTint = _root_ide_package_.com.example.baltazar.core.theme.PrimaryDark,
    inverseSurface = _root_ide_package_.com.example.baltazar.core.theme.InverseSurfaceDark,
    inverseOnSurface = _root_ide_package_.com.example.baltazar.core.theme.InverseOnSurfaceDark,
    error = _root_ide_package_.com.example.baltazar.core.theme.ErrorDark,
    onError = _root_ide_package_.com.example.baltazar.core.theme.OnErrorDark,
    errorContainer = _root_ide_package_.com.example.baltazar.core.theme.ErrorContainerDark,
    onErrorContainer = _root_ide_package_.com.example.baltazar.core.theme.OnErrorContainerDark,
    outline = _root_ide_package_.com.example.baltazar.core.theme.OutlineDark,
    outlineVariant = _root_ide_package_.com.example.baltazar.core.theme.OutlineVariantDark,
    scrim = _root_ide_package_.com.example.baltazar.core.theme.ScrimDark,
    surfaceBright = _root_ide_package_.com.example.baltazar.core.theme.SurfaceBrightDark,
    surfaceContainer = _root_ide_package_.com.example.baltazar.core.theme.SurfaceContainerDark,
    surfaceContainerHigh = _root_ide_package_.com.example.baltazar.core.theme.SurfaceContainerHighDark,
    surfaceContainerHighest = _root_ide_package_.com.example.baltazar.core.theme.SurfaceContainerHighestDark,
    surfaceContainerLow = _root_ide_package_.com.example.baltazar.core.theme.SurfaceContainerLowDark,
    surfaceContainerLowest = _root_ide_package_.com.example.baltazar.core.theme.SurfaceContainerLowestDark,
    surfaceDim = _root_ide_package_.com.example.baltazar.core.theme.SurfaceDimDark
)

@Composable
fun BaltazarAppTheme(
    darkTheme: Boolean = false,
    dynamicColor: Boolean = true,
    content: @Composable () -> Unit
) {
    val colorScheme = when {
        dynamicColor && Build.VERSION.SDK_INT >= Build.VERSION_CODES.S -> {
            val context = LocalContext.current
            if (darkTheme) dynamicDarkColorScheme(context) else dynamicLightColorScheme(context)
        }

        darkTheme -> _root_ide_package_.com.example.baltazar.core.theme.DarkColorScheme
        else -> _root_ide_package_.com.example.baltazar.core.theme.LightColorScheme
    }

    MaterialTheme(
        colorScheme = colorScheme,
        typography = _root_ide_package_.com.example.baltazar.core.theme.Typography,
        content = content
    )
}