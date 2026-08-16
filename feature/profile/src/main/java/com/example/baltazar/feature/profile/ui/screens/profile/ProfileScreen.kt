package com.example.baltazar.feature.profile.ui.screens.profile

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Switch
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import com.example.baltazar.core.R
import com.example.baltazar.core.core.components.CustomAlertDialog
import com.example.baltazar.core.core.components.CustomAppBar
import com.example.baltazar.core.core.constants.Paddings
import com.example.baltazar.core.core.constants.Spaces
import com.example.baltazar.core.core.enums.CardViewMode
import com.example.baltazar.core.core.enums.HomeTab
import com.example.baltazar.core.core.enums.TitleAlignment
import com.example.baltazar.core.core.navigation.About
import com.example.baltazar.core.core.navigation.AuthSelection
import com.example.baltazar.core.core.navigation.Help
import com.example.baltazar.core.core.navigation.Home
import com.example.baltazar.core.core.navigation.ProfileDriverLicense
import com.example.baltazar.core.core.navigation.ProfilePassport
import com.example.baltazar.core.core.navigation.ProfilePersonalInfo
import com.example.baltazar.core.core.navigation.ProfileUserDetail
import com.example.baltazar.feature.profile.ui.screens.profile.components.CardStyleBottomSheet
import com.example.baltazar.feature.profile.ui.screens.profile.components.LanguageBottomSheet
import com.example.baltazar.feature.profile.ui.screens.profile.components.ProfileBanner
import com.example.baltazar.feature.profile.ui.screens.profile.components.ProfileTile
import com.example.baltazar.feature.profile.ui.screens.profile.components.RegionBottomSheet
import compose.icons.TablerIcons
import compose.icons.tablericons.Car
import compose.icons.tablericons.Headset
import compose.icons.tablericons.Heart
import compose.icons.tablericons.Id
import compose.icons.tablericons.InfoCircle
import compose.icons.tablericons.Language
import compose.icons.tablericons.LayoutGrid
import compose.icons.tablericons.Logout
import compose.icons.tablericons.MapPin
import compose.icons.tablericons.Moon
import compose.icons.tablericons.Receipt
import compose.icons.tablericons.UserCheck

@Composable
fun ProfileScreen(
    navController: NavHostController,
    onNavigateToTab: (HomeTab) -> Unit = {},
    viewModel: ProfileViewModel = hiltViewModel()
) {
    val state by viewModel.state.collectAsState()
    val context = LocalContext.current
    val isGuest = state.user.role == "GUEST"

    LaunchedEffect(state.isLoggedOut) {
        if (state.isLoggedOut) {
            try {
                navController.navigate(AuthSelection) {
                    popUpTo<Home> {
                        inclusive = true
                    }
                }
            } catch (_: Exception) {
                navController.navigate(AuthSelection)
            }
        }
    }

    Scaffold(
        topBar = {
            CustomAppBar(
                title = stringResource(R.string.profile_title),
                alignment = TitleAlignment.CENTER
            )
        },
        containerColor = MaterialTheme.colorScheme.background
    ) { paddingValues ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
                .verticalScroll(rememberScrollState())
                .padding(horizontal = Paddings.Medium, vertical = Paddings.Small),
            verticalArrangement = Arrangement.spacedBy(Spaces.LargeMinus)
        ) {
            // Profile Banner
            ProfileBanner(
                user = state.user,
                onClick = {
                    if (isGuest) {
                        navController.navigate(AuthSelection)
                    } else {
                        navController.navigate(ProfileUserDetail)
                    }
                }
            )

            // Section 1: Profil
            Column(
                modifier = Modifier.fillMaxWidth(),
                verticalArrangement = Arrangement.spacedBy(Spaces.ExtraSmall)
            ) {
                Text(
                    text = stringResource(R.string.profile_section_profile),
                    style = MaterialTheme.typography.titleSmall,
                    fontWeight = FontWeight.Bold,
                    color = MaterialTheme.colorScheme.primary,
                    modifier = Modifier.padding(start = Paddings.Mini, bottom = Paddings.ExtraMini)
                )

                // Wishlist
                ProfileTile(
                    title = stringResource(R.string.profile_wishlist),
                    icon = TablerIcons.Heart,
                    onClick = { onNavigateToTab(HomeTab.Wishlist) }
                )

                // Orders
                ProfileTile(
                    title = stringResource(R.string.orders_title),
                    icon = TablerIcons.Receipt,
                    onClick = { onNavigateToTab(HomeTab.Orders) }
                )

                // Show personal details only when NOT guest
                if (!isGuest) {
                    // Personal Info
                    ProfileTile(
                        title = stringResource(R.string.profile_personal_info),
                        icon = TablerIcons.UserCheck,
                        onClick = { navController.navigate(ProfilePersonalInfo) }
                    )

                    // Passport Info
                    ProfileTile(
                        title = stringResource(R.string.profile_passport),
                        icon = TablerIcons.Id,
                        onClick = { navController.navigate(ProfilePassport) }
                    )

                    // Driver License
                    ProfileTile(
                        title = stringResource(R.string.profile_driver_license),
                        icon = TablerIcons.Car,
                        onClick = { navController.navigate(ProfileDriverLicense) }
                    )
                }
            }

            // Section 2: Ayarlar
            Column(
                modifier = Modifier.fillMaxWidth(),
                verticalArrangement = Arrangement.spacedBy(Spaces.ExtraSmall)
            ) {
                Text(
                    text = stringResource(R.string.profile_section_settings),
                    style = MaterialTheme.typography.titleSmall,
                    fontWeight = FontWeight.Bold,
                    color = MaterialTheme.colorScheme.primary,
                    modifier = Modifier.padding(start = Paddings.Mini, bottom = Paddings.ExtraMini)
                )

                // Theme Mode Switch
                ProfileTile(
                    title = stringResource(R.string.profile_theme_mode),
                    icon = TablerIcons.Moon,
                    trailingContent = {
                        Switch(
                            checked = state.isDarkMode,
                            onCheckedChange = viewModel::changeTheme
                        )
                    }
                )

                // Language Bottom Sheet
                ProfileTile(
                    title = stringResource(R.string.profile_language),
                    icon = TablerIcons.Language,
                    trailingValue = state.selectedLanguage.displayName,
                    onClick = { viewModel.openLanguageSheet() }
                )

                // Region Bottom Sheet
                ProfileTile(
                    title = stringResource(R.string.profile_region),
                    icon = TablerIcons.MapPin,
                    trailingValue = state.selectedRegion.displayName,
                    onClick = { viewModel.openRegionSheet() }
                )

                // Card Style Bottom Sheet
                val cardModeText = if (state.cardViewMode == CardViewMode.GRID) {
                    stringResource(R.string.card_style_grid)
                } else {
                    stringResource(R.string.card_style_list)
                }

                ProfileTile(
                    title = stringResource(R.string.profile_card_style),
                    icon = TablerIcons.LayoutGrid,
                    trailingValue = cardModeText,
                    onClick = { viewModel.openCardStyleSheet() }
                )

                // About
                ProfileTile(
                    title = stringResource(R.string.profile_about),
                    icon = TablerIcons.InfoCircle,
                    onClick = { navController.navigate(About) }
                )

                // Help & Support
                ProfileTile(
                    title = stringResource(R.string.help_title),
                    icon = TablerIcons.Headset,
                    onClick = { navController.navigate(Help) }
                )

                // Logout
                ProfileTile(
                    title = stringResource(R.string.profile_logout),
                    icon = TablerIcons.Logout,
                    onClick = { viewModel.openLogoutDialog() }
                )
            }

            Spacer(modifier = Modifier.height(Spaces.ExtraLarge))
        }

        // Modals
        if (state.isLanguageSheetOpen) {
            LanguageBottomSheet(
                selectedLanguage = state.selectedLanguage,
                onLanguageSelected = { lang ->
                    viewModel.setLanguage(context, lang)
                },
                onDismissRequest = viewModel::closeLanguageSheet
            )
        }

        if (state.isRegionSheetOpen) {
            RegionBottomSheet(
                selectedRegion = state.selectedRegion,
                onRegionSelected = { reg ->
                    viewModel.setRegion(reg)
                },
                onDismissRequest = viewModel::closeRegionSheet
            )
        }

        if (state.isCardStyleSheetOpen) {
            CardStyleBottomSheet(
                currentMode = state.cardViewMode,
                onModeSelected = viewModel::setCardViewMode,
                onDismissRequest = viewModel::closeCardStyleSheet
            )
        }

        // Logout Confirmation Dialog
        if (state.isLogoutDialogOpen) {
            CustomAlertDialog(
                title = stringResource(R.string.profile_logout_dialog_title),
                subtitle = stringResource(R.string.profile_logout_dialog_subtitle),
                confirmText = stringResource(R.string.profile_logout),
                cancelText = stringResource(R.string.cancel),
                isDestructive = true,
                onConfirm = viewModel::confirmLogout,
                onCancel = viewModel::closeLogoutDialog,
                onDismissRequest = viewModel::closeLogoutDialog
            )
        }
    }
}
