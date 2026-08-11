package com.example.baltazar.ui.screens.home


import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.NavigationBarItemDefaults
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import com.baltazar.app.R
import com.example.baltazar.core.enums.HomeTab
import com.example.baltazar.domain.model.BottomTabItem
import com.example.baltazar.feature.explore.ui.screens.explore.ExploreScreen
import com.example.baltazar.feature.profile.ui.screens.profile.ProfileScreen
import com.example.baltazar.feature.profile.ui.screens.wishlist.WishlistScreen
import compose.icons.TablerIcons
import compose.icons.tablericons.Compass
import compose.icons.tablericons.Heart
import compose.icons.tablericons.User

@Composable
fun HomeScreen(
    navController: NavHostController,
) {
    var selectedTab by rememberSaveable { mutableStateOf(HomeTab.Explore) }

    val bottomTabs = listOf(
        BottomTabItem(HomeTab.Explore, TablerIcons.Compass, R.string.bottom_nav_explore),
        BottomTabItem(HomeTab.Wishlist, TablerIcons.Heart, R.string.bottom_nav_wishlist),
        BottomTabItem(HomeTab.Profile, TablerIcons.User, R.string.bottom_nav_profile)
    )
    Scaffold(
        bottomBar = {
            NavigationBar(
                containerColor = MaterialTheme.colorScheme.surface,
                modifier = Modifier.height(72.dp)
            ) {
                bottomTabs.forEach { item ->
                    NavigationBarItem(
                        selected = selectedTab == item.tab,
                        onClick = { selectedTab = item.tab },
                        icon = {
                            Icon(
                                imageVector = item.icon,
                                contentDescription = stringResource(item.label)
                            )
                        },
                        colors = NavigationBarItemDefaults.colors(
                            selectedIconColor = MaterialTheme.colorScheme.primary,
                            unselectedIconColor = MaterialTheme.colorScheme.onSurfaceVariant
                        )
                    )
                }
            }
        }
    ) { paddingValues ->
        val modifier = Modifier
            .fillMaxSize()
            .padding(paddingValues)

        Surface(modifier = modifier, color = MaterialTheme.colorScheme.background) {
            when (selectedTab) {
                HomeTab.Explore -> {
                    ExploreScreen(navController)
                }

                HomeTab.Wishlist -> {
                    WishlistScreen(navController)

                }

                HomeTab.Profile -> {
                    ProfileScreen(navController)

                }
            }
        }
    }
}
