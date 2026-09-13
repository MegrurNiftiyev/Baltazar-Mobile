package com.example.baltazar.feature.profile.ui.screens.wishlist

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.pulltorefresh.PullToRefreshBox
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import com.example.baltazar.core.R
import com.example.baltazar.core.core.components.CustomAppBar
import com.example.baltazar.core.core.components.EmptyStateView
import com.example.baltazar.core.core.components.StandardItemCard
import com.example.baltazar.core.core.constants.Paddings
import com.example.baltazar.core.core.constants.Spaces
import com.example.baltazar.core.core.enums.CardViewMode
import com.example.baltazar.core.core.enums.HomeTab
import com.example.baltazar.core.core.enums.ServiceType
import com.example.baltazar.core.core.enums.TitleAlignment
import com.example.baltazar.core.core.extensions.consumeResult
import com.example.baltazar.core.core.extensions.navigateToServiceDetail
import com.example.baltazar.core.core.navigation.LikeResult
import com.example.baltazar.core.core.navigation.Login
import com.example.baltazar.core.core.navigation.NavResultKeys
import compose.icons.TablerIcons
import compose.icons.tablericons.Heart

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun WishlistScreen(
    navController: NavHostController,
    onNavigateToTab: (HomeTab) -> Unit = {},
    viewModel: WishlistViewModel = hiltViewModel()
) {
    val state by viewModel.state.collectAsState()

    val isGuest = state.user.isGuest

    LaunchedEffect(Unit) {
        viewModel.loadWishlist()
    }

    val currentEntry = navController.currentBackStackEntry
    val likeResult = currentEntry?.consumeResult<LikeResult>(NavResultKeys.LIKE_RESULT)
    LaunchedEffect(likeResult) {
        likeResult?.let { result ->
            viewModel.toggleFavoriteById(result.itemId, result.isLiked)
        }
    }

    Scaffold(
        topBar = {
            CustomAppBar(
                title = stringResource(R.string.wishlist_title),
                alignment = TitleAlignment.CENTER
            )
        },
        containerColor = MaterialTheme.colorScheme.background
    ) { paddingValues ->
        PullToRefreshBox(
            isRefreshing = state.isRefreshing,
            onRefresh = { viewModel.refreshWishlist() },
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
        ) {
            when {
                // Case 1: Loading user session or loading initial wishlist items
                state.isUserLoading || (state.isLoading && state.items.isEmpty()) -> {
                    val columnCount = if (state.cardViewMode == CardViewMode.GRID) 2 else 1
                    LazyVerticalGrid(
                        columns = GridCells.Fixed(columnCount),
                        contentPadding = PaddingValues(Paddings.Medium),
                        horizontalArrangement = Arrangement.spacedBy(Spaces.Medium),
                        verticalArrangement = Arrangement.spacedBy(Spaces.Medium),
                        modifier = Modifier.fillMaxSize()
                    ) {
                        items(6) {
                            StandardItemCard(
                                onClick = {},
                                isLoading = true,
                                cardViewMode = state.cardViewMode
                            )
                        }
                    }
                }

                // Case 2: User is Guest (not logged in)
                isGuest -> {
                    Box(
                        modifier = Modifier.fillMaxSize(),
                        contentAlignment = Alignment.Center
                    ) {
                        EmptyStateView(
                            icon = TablerIcons.Heart,
                            title = stringResource(R.string.wishlist_guest_title),
                            subtitle = stringResource(R.string.wishlist_guest_subtitle),
                            actionButtonText = stringResource(R.string.login),
                            onActionClick = { navController.navigate(Login(isBackPrevious = true)) }
                        )
                    }
                }

                // Case 3: Empty wishlist
                state.items.isEmpty() -> {
                    Box(
                        modifier = Modifier.fillMaxSize(),
                        contentAlignment = Alignment.Center
                    ) {
                        EmptyStateView(
                            icon = TablerIcons.Heart,
                            title = stringResource(R.string.wishlist_empty_title),
                            subtitle = stringResource(R.string.wishlist_empty_subtitle),
                            actionButtonText = stringResource(R.string.explore_services),
                            onActionClick = { onNavigateToTab(HomeTab.Explore) }
                        )
                    }
                }

                // Case 4: Wishlist items list
                else -> {
                    val columnCount = if (state.cardViewMode == CardViewMode.GRID) 2 else 1
                    LazyVerticalGrid(
                        columns = GridCells.Fixed(columnCount),
                        contentPadding = PaddingValues(Paddings.Medium),
                        horizontalArrangement = Arrangement.spacedBy(Spaces.Medium),
                        verticalArrangement = Arrangement.spacedBy(Spaces.Medium),
                        modifier = Modifier.fillMaxSize()
                    ) {
                        items(
                            items = state.items,
                            key = { it.id }
                        ) { item ->
                            StandardItemCard(
                                onClick = { navController.navigateToServiceDetail(item) },
                                cardViewMode = state.cardViewMode,
                                isLoading = false,
                                imageUrl = item.image,
                                title = item.title,
                                subtitle = item.category,
                                price = item.price,
                                currency = item.currency,
                                priceSuffix = item.priceSuffix,
                                rating = item.rating,
                                isFavorite = true,
                                onFavoriteClick = { isFav ->
                                    viewModel.toggleFavorite(item, isFav)
                                }
                            )
                        }
                    }
                }
            }
        }
    }
}

