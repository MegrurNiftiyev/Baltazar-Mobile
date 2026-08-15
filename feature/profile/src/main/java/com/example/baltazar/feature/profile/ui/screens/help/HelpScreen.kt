package com.example.baltazar.feature.profile.ui.screens.help

import android.content.Intent
import android.net.Uri
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import com.example.baltazar.core.R
import com.example.baltazar.core.core.components.CustomAppBar
import com.example.baltazar.core.core.components.CustomCard
import com.example.baltazar.core.core.components.CustomLargeCard
import com.example.baltazar.core.core.constants.BorderRadiuses
import com.example.baltazar.core.core.constants.Paddings
import com.example.baltazar.core.core.constants.Spaces
import com.example.baltazar.core.core.enums.TitleAlignment
import compose.icons.TablerIcons
import compose.icons.tablericons.Headset
import compose.icons.tablericons.Mail

@Composable
fun HelpScreen(
    navController: NavHostController
) {
    val context = LocalContext.current
    val email = stringResource(R.string.help_email_value)

    Scaffold(
        topBar = {
            CustomAppBar(
                title = stringResource(R.string.help_title),
                alignment = TitleAlignment.CENTER,
                onBackClick = { navController.popBackStack() }
            )
        },
        containerColor = MaterialTheme.colorScheme.background
    ) { paddingValues ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
                .verticalScroll(rememberScrollState())
                .padding(Paddings.LargeMinus),
            verticalArrangement = Arrangement.spacedBy(Spaces.Medium)
        ) {
            CustomLargeCard(
                title = stringResource(R.string.help_title),
                subtitle = stringResource(R.string.help_subtitle),
                icon = TablerIcons.Headset
            )

            CustomCard(
                icon = TablerIcons.Mail,
                label = stringResource(R.string.help_email_label),
                value = email
            )

            Spacer(modifier = Modifier.height(Spaces.ExtraSmall))

            Button(
                onClick = {
                    val intent = Intent(Intent.ACTION_SENDTO).apply {
                        data = Uri.parse("mailto:$email")
                        putExtra(Intent.EXTRA_SUBJECT, "Baltazar Support")
                    }
                    context.startActivity(Intent.createChooser(intent, "Send Email"))
                },
                modifier = Modifier
                    .fillMaxWidth()
                    .height(50.dp),
                shape = RoundedCornerShape(BorderRadiuses.Small)
            ) {
                Text(text = stringResource(R.string.help_send_email))
            }
        }
    }
}
