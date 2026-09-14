package com.example.baltazar.feature.travel.ui.screens.travels

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.GridItemSpan
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.lazy.grid.rememberLazyGridState
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.pulltorefresh.PullToRefreshBox
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import com.example.baltazar.core.core.components.CustomAppBar
import com.example.baltazar.core.core.components.EmptyStateView
import com.example.baltazar.core.core.components.filters.FilterChipItem
import com.example.baltazar.core.core.components.filters.FilterOption
import com.example.baltazar.core.core.components.filters.FloatingShowResultsButton
import com.example.baltazar.core.core.components.filters.SingleSelectFilterDialog
import com.example.baltazar.core.core.constants.IconSizes
import com.example.baltazar.core.core.constants.Paddings
import com.example.baltazar.core.core.constants.Spaces
import com.example.baltazar.core.core.enums.CardViewMode
import com.example.baltazar.core.core.enums.TitleAlignment
import com.example.baltazar.core.core.extensions.consumeResult
import com.example.baltazar.core.core.navigation.CompanyList
import com.example.baltazar.core.core.navigation.LikeResult
import com.example.baltazar.core.core.managers.requireAuth
import com.example.baltazar.core.core.navigation.Login
import com.example.baltazar.core.core.navigation.NavResultKeys
import com.example.baltazar.core.core.navigation.TravelDetail
import com.example.baltazar.core.R as CoreR
import com.example.baltazar.feature.travel.R
import com.example.baltazar.feature.travel.ui.components.TourCard
import compose.icons.TablerIcons
import compose.icons.tablericons.Building
import compose.icons.tablericons.Plane

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TravelsScreen(
    navController: NavHostController,
    viewModel: TravelsViewModel = hiltViewModel()
) {
    val state by viewModel.state.collectAsState()
    val listState = rememberLazyGridState()

    var showRatingDialog by remember { mutableStateOf(false) }

    val currentEntry = navController.currentBackStackEntry
    val likeResult = currentEntry?.consumeResult<LikeResult>(NavResultKeys.LIKE_RESULT)
    LaunchedEffect(likeResult) {
        likeResult?.let { result ->
            viewModel.toggleFavorite(result.itemId, result.isLiked)
        }
    }

    val shouldLoadMore by remember {
        derivedStateOf {
            val lastVisibleItem = listState.layoutInfo.visibleItemsInfo.lastOrNull()
            lastVisibleItem != null && lastVisibleItem.index >= listState.layoutInfo.totalItemsCount - 2
        }
    }

    LaunchedEffect(shouldLoadMore) {
        if (shouldLoadMore && !state.isLoading && !state.isPaginationLoading && state.hasMore) {
            viewModel.loadNextPage()
        }
    }

    if (showRatingDialog) {
        val ratingOptions = listOf(
            FilterOption(3.0, "3.0+ ★"),
            FilterOption(4.0, "4.0+ ★"),
            FilterOption(4.5, "4.5+ ★")
        )
        SingleSelectFilterDialog(
            title = stringResource(id = CoreR.string.filter_star_rating),
            options = ratingOptions,
            selectedOption = state.draftMinRating,
            onApply = { rating ->
                viewModel.setDraftMinRating(rating)
                showRatingDialog = false
            },
            onDismiss = { showRatingDialog = false }
        )
    }

    Scaffold(
        topBar = {
            Column {
                CustomAppBar(
                    title = stringResource(id = R.string.travel_title),
                    alignment = TitleAlignment.CENTER,
                    onBackClick = { navController.popBackStack() },
                    trailingContent = {
                        IconButton(
                            onClick = { navController.navigate(CompanyList(serviceType = "TRAVEL")) }
                        ) {
                            Icon(
                                imageVector = TablerIcons.Building,
                                contentDescription = null,
                                modifier = Modifier.size(IconSizes.Large),
                                tint = MaterialTheme.colorScheme.primary
                            )
                        }
                    }
                )
                LazyRow(
                    contentPadding = PaddingValues(horizontal = Paddings.Medium, vertical = Paddings.Small),
                    horizontalArrangement = Arrangement.spacedBy(Spaces.Small),
                    modifier = Modifier.fillMaxWidth()
                ) {
                    item {
                        val ratingLabel = state.draftMinRating?.let { "$it+ ★" }
                        FilterChipItem(
                            label = stringResource(id = CoreR.string.filter_star_rating),
                            selectedValue = ratingLabel,
                            isActive = state.draftMinRating != null,
                            onClick = { showRatingDialog = true },
                            onClearClick = if (state.draftMinRating != null) {
                                { viewModel.setDraftMinRating(null) }
                            } else null
                        )
                    }
                }
            }
        },
        containerColor = MaterialTheme.colorScheme.background
    ) { paddingValues ->
        val columnCount = if (state.cardViewMode == CardViewMode.GRID) 2 else 1

        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
        ) {
            PullToRefreshBox(
                isRefreshing = state.isLoading && state.items.isNotEmpty(),
                onRefresh = { viewModel.loadInitialData() },
                modifier = Modifier.fillMaxSize()
            ) {
                if (!state.isLoading && state.items.isEmpty()) {
                    EmptyStateView(
                        icon = TablerIcons.Plane,
                        title = "Axtarışa uyğun nəticə tapılmadı",
                        subtitle = "Daxil etdiyiniz filter kriteriyalarına uyğun heç bir tur tapılmadı.",
                        modifier = Modifier.fillMaxSize()
                    )
                } else {
                    LazyVerticalGrid(
                        state = listState,
                        columns = GridCells.Fixed(columnCount),
                        contentPadding = PaddingValues(Paddings.Medium),
                        horizontalArrangement = Arrangement.spacedBy(Spaces.Medium),
                        verticalArrangement = Arrangement.spacedBy(Spaces.Medium),
                        modifier = Modifier.fillMaxSize()
                    ) {
                        if (state.isLoading && state.items.isEmpty()) {
                            items(6) {
                                TourCard(
                                    tour = null,
                                    isLoading = true,
                                    cardViewMode = state.cardViewMode,
                                    onClick = {}
                                )
                            }
                        } else {
                            items(
                                items = state.items,
                                key = { it.id }
                            ) { item ->
                                TourCard(
                                    tour = item,
                                    isLoading = false,
                                    cardViewMode = state.cardViewMode,
                                    isFavorite = item.isLiked,
                                    onFavoriteClick = { isFav ->
                                        viewModel.authGateManager.requireAuth(navController) {
                                            viewModel.toggleFavorite(item.id, isFav)
                                        }
                                    },
                                    onClick = { navController.navigate(TravelDetail(item.id)) }
                                )
                            }

                            if (state.isPaginationLoading) {
                                item(span = { GridItemSpan(columnCount) }) {
                                    Box(
                                        modifier = Modifier
                                            .fillMaxWidth()
                                            .padding(Paddings.Medium),
                                        contentAlignment = Alignment.Center
                                    ) {
                                        CircularProgressIndicator()
                                    }
                                }
                            }
                        }
                    }
                }
            }

            FloatingShowResultsButton(
                isVisible = state.hasDraftChanges,
                onClick = { viewModel.applyFilters() },
                modifier = Modifier.align(Alignment.BottomCenter)
            )
        }
    }
}
