package com.example.baltazar.feature.company.ui.components.sections

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import com.example.baltazar.core.R
import com.example.baltazar.core.core.components.DetailAboutSection

@Composable
fun AboutSectionComponent(
    aboutText: String,
    modifier: Modifier = Modifier
) {
    if (aboutText.isBlank()) return

    DetailAboutSection(
        title = stringResource(R.string.about_service_title),
        description = aboutText,
        modifier = modifier
    )
}
