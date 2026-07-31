package com.example.baltazar.feature.auth.ui.screens.onboarding.components

import androidx.annotation.StringRes
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import com.example.baltazar.core.constants.Paddings
import com.example.baltazar.core.constants.Spaces

@Composable
fun OnboardingPageItem(
    @StringRes title: Int,
    @StringRes description: Int,
    imageSource: Any,
    modifier: Modifier = Modifier
) {
    Column(
        modifier = modifier.fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        AsyncImage(
            model = imageSource,
            contentDescription = null,
            contentScale = ContentScale.Fit,
            modifier = Modifier
                .size(260.dp)
                .padding(Paddings.Medium)
        )

        Spacer(modifier = Modifier.height(Spaces.Large))

        Text(
            text = stringResource(title),
            style = MaterialTheme.typography.headlineSmall,
            color = MaterialTheme.colorScheme.onBackground,
            textAlign = TextAlign.Center
        )

        Spacer(modifier = Modifier.height(Spaces.Small))

        Text(
            text = stringResource(description),
            style = MaterialTheme.typography.bodyMedium,
            color = MaterialTheme.colorScheme.onSurfaceVariant,
            textAlign = TextAlign.Center,
            modifier = Modifier.padding(horizontal = Paddings.Medium)
        )
    }
}
