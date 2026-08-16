package com.example.baltazar.feature.company.ui.components.sections

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import com.example.baltazar.core.R
import com.example.baltazar.core.core.components.DetailAboutSection

@Composable
fun CompanyAboutSectionComponent(
    about: String,
    modifier: Modifier = Modifier
) {
    if (about.isNotBlank()) {
        DetailAboutSection(
            title = stringResource(R.string.company_detail),
            description = about,
            modifier = modifier
        )
    }
}
