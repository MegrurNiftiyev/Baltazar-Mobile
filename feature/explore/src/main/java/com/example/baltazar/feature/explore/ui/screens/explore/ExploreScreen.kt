package com.example.baltazar.feature.explore.ui.screens.explore

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.DrawerValue
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ModalNavigationDrawer
import androidx.compose.material3.Scaffold
import androidx.compose.material3.pulltorefresh.PullToRefreshBox
import androidx.compose.material3.rememberDrawerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import com.example.baltazar.core.core.components.CustomAppBar
import com.example.baltazar.core.core.components.ShimmerWrapper
import com.example.baltazar.core.core.constants.Paddings
import com.example.baltazar.core.core.constants.Spaces
import com.example.baltazar.core.core.enums.HomeTab
import com.example.baltazar.core.core.enums.ServiceType
import com.example.baltazar.core.core.enums.TitleAlignment
import com.example.baltazar.core.core.navigation.Cart
import com.example.baltazar.core.core.navigation.FoodDetail
import com.example.baltazar.core.core.navigation.FoodList
import com.example.baltazar.core.core.navigation.Home
import com.example.baltazar.core.core.navigation.HotelDetail
import com.example.baltazar.core.core.navigation.HotelList
import com.example.baltazar.core.core.navigation.RentACarDetail
import com.example.baltazar.core.core.navigation.RentACarList
import com.example.baltazar.core.core.navigation.Settings
import com.example.baltazar.core.core.navigation.TravelDetail
import com.example.baltazar.core.core.navigation.TravelList
import com.example.baltazar.core.core.utils.AppSnackbar
import com.example.baltazar.core.core.utils.SnackbarType
import com.example.baltazar.core.domain.model.ServiceCardItem
import com.example.baltazar.feature.explore.ui.components.BannerCarousel
import com.example.baltazar.feature.explore.ui.components.ExploreErrorFallback
import com.example.baltazar.feature.explore.ui.components.ExploreSectionRow
import com.example.baltazar.feature.explore.ui.components.ExploreServiceQuickActions
import com.example.baltazar.feature.explore.ui.components.ExploreSidebar
import compose.icons.TablerIcons
import compose.icons.tablericons.Menu2
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ExploreScreen(
    navController: NavHostController,
    onNavigateToTab: (HomeTab) -> Unit = {},
    viewModel: ExploreViewModel = hiltViewModel()
) {
    val context = LocalContext.current
    val state by viewModel.state.collectAsState()
    val drawerState = rememberDrawerState(initialValue = DrawerValue.Closed)
    val coroutineScope = rememberCoroutineScope()

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

    fun navigateToService(serviceType: ServiceType) {
        when (serviceType) {
            ServiceType.RENT_A_CAR -> navController.navigate(RentACarList)
            ServiceType.HOTEL -> navController.navigate(HotelList)
            ServiceType.TRAVEL -> navController.navigate(TravelList)
            ServiceType.FOOD -> navController.navigate(FoodList)
            ServiceType.UNKNOWN -> {}
        }
    }

    fun navigateToDetail(item: ServiceCardItem) {
        when (item.serviceType) {
            ServiceType.RENT_A_CAR -> navController.navigate(RentACarDetail(item.serviceId))
            ServiceType.HOTEL -> navController.navigate(HotelDetail(item.serviceId))
            ServiceType.TRAVEL -> navController.navigate(TravelDetail(item.serviceId))
            ServiceType.FOOD -> navController.navigate(FoodDetail(item.serviceId))
            ServiceType.UNKNOWN -> {}
        }
    }

    val isRefreshing = state.isSectionsLoading || state.isBannersLoading

    ModalNavigationDrawer(
        drawerState = drawerState,
        drawerContent = {
            ExploreSidebar(
                user = state.user,
                isUserLoading = state.isUserLoading,
                onPageClick = { route ->
                    coroutineScope.launch { drawerState.close() }
                    when (route) {
                        is Cart -> onNavigateToTab(HomeTab.Orders)
                        is Settings -> onNavigateToTab(HomeTab.Profile)
                        else -> navController.navigate(route)
                    }
                },
                onUserClick = {
                    coroutineScope.launch { drawerState.close() }
                    onNavigateToTab(HomeTab.Profile)
                }
            )
        }
    ) {
        Scaffold(
            topBar = {
                CustomAppBar(
                    title = "Baltazar",
                    alignment = TitleAlignment.START,
                    leadingContent = {
                        ShimmerWrapper(isLoading = state.isUserLoading) {
                            com.example.baltazar.core.core.components.UserAvatar.UserAvatar(
                                user = state.user,
                                size = 36.dp
                            )
                        }
                    },
                    trailingContent = {
                        IconButton(onClick = { coroutineScope.launch { drawerState.open() } }) {
                            Icon(
                                imageVector = TablerIcons.Menu2,
                                contentDescription = "Menu",
                                tint = MaterialTheme.colorScheme.onSurface
                            )
                        }
                    }
                )
            },
            containerColor = MaterialTheme.colorScheme.background
        ) { innerPadding ->
            PullToRefreshBox(
                isRefreshing = isRefreshing,
                onRefresh = { viewModel.loadExploreData() },
                modifier = Modifier
                    .fillMaxSize()
                    .padding(innerPadding)
            ) {
                LazyColumn(
                    modifier = Modifier.fillMaxSize(),
                    contentPadding = PaddingValues(bottom = Paddings.Large)
                ) {
                    item {
                        BannerCarousel(
                            banners = state.banners,
                            isLoading = state.isBannersLoading,
                            isError = state.isBannersError,
                            onRetry = { viewModel.loadExploreData() },
                            onBannerClick = { banner ->
                                navigateToService(banner.serviceType)
                            }
                        )
                        Spacer(modifier = Modifier.height(Spaces.Large))
                        ExploreServiceQuickActions(
                            onServiceClick = { serviceType ->
                                navigateToService(serviceType)
                            },
                            isLoading = state.isSectionsLoading
                        )
                        Spacer(modifier = Modifier.height(Spaces.Large))
                    }

                    if (state.isSectionsError && !state.isSectionsLoading) {
                        item {
                            com.example.baltazar.core.core.components.ErrorBox(
                                onRetry = { viewModel.loadExploreData() }
                            )
                        }
                    } else {
                        val sectionsToDisplay = if (state.isSectionsLoading && state.sections.isEmpty()) {
                            List(3) { com.example.baltazar.feature.explore.domain.model.ExploreSection(ServiceType.UNKNOWN, "mock", emptyList(), 0) }
                        } else {
                            state.sections
                        }

                        items(sectionsToDisplay) { section ->
                            ExploreSectionRow(
                                section = section,
                                isLoading = state.isSectionsLoading,
                                onItemClick = { item -> navigateToDetail(item) },
                                onSeeAllClick = { navigateToService(section.serviceType) },
                                modifier = Modifier.padding(bottom = Spaces.Large)
                            )
                        }
                    }
                }
            }
        }
    }
}
