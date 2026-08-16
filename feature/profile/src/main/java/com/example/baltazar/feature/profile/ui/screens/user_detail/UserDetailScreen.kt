package com.example.baltazar.feature.profile.ui.screens.user_detail

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import com.example.baltazar.core.R
import com.example.baltazar.core.core.components.CustomAppBar
import com.example.baltazar.core.core.components.RoundedButton
import com.example.baltazar.core.core.constants.BorderRadiuses
import com.example.baltazar.core.core.constants.Paddings
import com.example.baltazar.core.core.constants.Spaces
import com.example.baltazar.core.core.enums.CornerShape
import com.example.baltazar.core.core.enums.TitleAlignment
import com.example.baltazar.core.core.utils.AppSnackbar
import com.example.baltazar.core.core.utils.SnackbarType
import com.example.baltazar.feature.profile.ui.screens.profile.components.LanguageBottomSheet
import com.example.baltazar.feature.profile.ui.screens.profile.components.ProfileTile
import com.example.baltazar.feature.profile.ui.screens.profile.components.RegionBottomSheet
import compose.icons.TablerIcons
import compose.icons.tablericons.Language
import compose.icons.tablericons.MapPin

@Composable
fun UserDetailScreen(
    navController: NavHostController,
    viewModel: UserDetailViewModel = hiltViewModel()
) {
    val state by viewModel.state.collectAsState()
    val context = LocalContext.current

    LaunchedEffect(state.isSuccess) {
        if (state.isSuccess) {
            navController.popBackStack()
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

    Scaffold(
        topBar = {
            CustomAppBar(
                title = stringResource(R.string.edit_profile_title),
                alignment = TitleAlignment.CENTER,
                onBackClick = { navController.popBackStack() }
            )
        },
        containerColor = MaterialTheme.colorScheme.background
    ) { paddingValues ->
        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
        ) {
            if (state.isLoading && state.name.isEmpty()) {
                CircularProgressIndicator(modifier = Modifier.align(Alignment.Center))
            } else {
                Column(
                    modifier = Modifier
                        .fillMaxSize()
                        .verticalScroll(rememberScrollState())
                        .padding(Paddings.LargeMinus),
                    verticalArrangement = Arrangement.spacedBy(Spaces.Medium)
                ) {
                    OutlinedTextField(
                        value = state.name,
                        onValueChange = viewModel::onNameChange,
                        label = { Text(text = stringResource(R.string.name_label)) },
                        isError = state.nameError != null,
                        supportingText = state.nameError?.let { err ->
                            { Text(text = err.asString(context), color = MaterialTheme.colorScheme.error) }
                        },
                        modifier = Modifier.fillMaxWidth(),
                        shape = RoundedCornerShape(BorderRadiuses.Medium),
                        singleLine = true
                    )

                    OutlinedTextField(
                        value = state.phone,
                        onValueChange = viewModel::onPhoneChange,
                        label = { Text(text = stringResource(R.string.phone_label)) },
                        isError = state.phoneError != null,
                        supportingText = state.phoneError?.let { err ->
                            { Text(text = err.asString(context), color = MaterialTheme.colorScheme.error) }
                        },
                        modifier = Modifier.fillMaxWidth(),
                        shape = RoundedCornerShape(BorderRadiuses.Medium),
                        singleLine = true
                    )

                    // Region Tile
                    ProfileTile(
                        title = stringResource(R.string.region_label),
                        icon = TablerIcons.MapPin,
                        trailingValue = state.selectedRegion.displayName,
                        onClick = { viewModel.openRegionSheet() }
                    )

                    // Language Tile
                    ProfileTile(
                        title = stringResource(R.string.language_label),
                        icon = TablerIcons.Language,
                        trailingValue = state.selectedLanguage.displayName,
                        onClick = { viewModel.openLanguageSheet() }
                    )

                    Spacer(modifier = Modifier.height(Spaces.Small))

                    RoundedButton(
                        text = stringResource(R.string.save),
                        onClick = { viewModel.save() },
                        isLoading = state.isLoading,
                        enabled = !state.isLoading,
                        shape = CornerShape.Rounded,
                        borderRadius = BorderRadiuses.Medium,
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(50.dp)
                    )
                }
            }
        }

        if (state.isLanguageSheetOpen) {
            LanguageBottomSheet(
                selectedLanguage = state.selectedLanguage,
                onLanguageSelected = viewModel::setLanguage,
                onDismissRequest = viewModel::closeLanguageSheet
            )
        }

        if (state.isRegionSheetOpen) {
            RegionBottomSheet(
                selectedRegion = state.selectedRegion,
                onRegionSelected = viewModel::setRegion,
                onDismissRequest = viewModel::closeRegionSheet
            )
        }
    }
}
