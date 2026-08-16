package com.example.baltazar.feature.company.ui.components.sections

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.example.baltazar.core.core.components.DetailAboutSection

@Composable
fun AboutSectionComponent(
    aboutText: String,
    modifier: Modifier = Modifier
) {
    if (aboutText.isBlank()) return

    DetailAboutSection(
        title = "Haqqında",
        description = aboutText,
        modifier = modifier
    )
}
