package com.example.baltazar.feature.profile.core.navigation

import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.compose.composable
import com.example.baltazar.core.core.navigation.About
import com.example.baltazar.core.core.navigation.Help
import com.example.baltazar.core.core.navigation.Profile
import com.example.baltazar.core.core.navigation.ProfileDriverLicense
import com.example.baltazar.core.core.navigation.ProfilePassport
import com.example.baltazar.core.core.navigation.ProfilePersonalInfo
import com.example.baltazar.core.core.navigation.ProfileUserDetail
import com.example.baltazar.core.core.navigation.Wishlist
import com.example.baltazar.feature.profile.ui.screens.about.AboutScreen
import com.example.baltazar.feature.profile.ui.screens.driver_license.DriverLicenseScreen
import com.example.baltazar.feature.profile.ui.screens.help.HelpScreen
import com.example.baltazar.feature.profile.ui.screens.passport.PassportInfoScreen
import com.example.baltazar.feature.profile.ui.screens.personal_info.PersonalInfoScreen
import com.example.baltazar.feature.profile.ui.screens.profile.ProfileScreen
import com.example.baltazar.feature.profile.ui.screens.user_detail.UserDetailScreen
import com.example.baltazar.feature.profile.ui.screens.wishlist.WishlistScreen

fun NavGraphBuilder.profileNavGraph(navController: NavHostController) {
    composable<Profile> { ProfileScreen(navController) }
    composable<Wishlist> { WishlistScreen(navController) }
    composable<About> { AboutScreen(navController) }
    composable<Help> { HelpScreen(navController) }
    composable<ProfilePersonalInfo> { PersonalInfoScreen(navController) }
    composable<ProfileDriverLicense> { DriverLicenseScreen(navController) }
    composable<ProfilePassport> { PassportInfoScreen(navController) }
    composable<ProfileUserDetail> { UserDetailScreen(navController) }
}
