package com.example.baltazar.feature.food.ui.screens.foods

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
import com.example.baltazar.core.core.components.filters.PriceRangeFilterDialog
import com.example.baltazar.core.core.components.filters.SingleSelectFilterDialog
import com.example.baltazar.core.core.constants.IconSizes
import com.example.baltazar.core.core.constants.Paddings
import com.example.baltazar.core.core.constants.Spaces
import com.example.baltazar.core.core.enums.CardViewMode
import com.example.baltazar.core.core.enums.TitleAlignment
import com.example.baltazar.core.core.extensions.consumeResult
import com.example.baltazar.core.core.navigation.CompanyList
import com.example.baltazar.core.core.navigation.FoodDetail
import com.example.baltazar.core.core.navigation.LikeResult
import com.example.baltazar.core.core.managers.requireAuth
import com.example.baltazar.core.core.navigation.Login
import com.example.baltazar.core.core.navigation.NavResultKeys
import com.example.baltazar.feature.food.R
import com.example.baltazar.feature.food.ui.components.FoodCard
import compose.icons.TablerIcons
import compose.icons.tablericons.Building

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun FoodsScreen(
    navController: NavHostController,
    viewModel: FoodsViewModel = hiltViewModel()
) {
    val state by viewModel.state.collectAsState()
    val listState = rememberLazyGridState()

    var showPriceDialog by remember { mutableStateOf(false) }

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

    if (showPriceDialog) {
        PriceRangeFilterDialog(
            initialMinPrice = state.draftMinPrice,
            initialMaxPrice = state.draftMaxPrice,
            onApply = { min, max ->
                viewModel.setDraftPrice(min, max)
                showPriceDialog = false
            },
            onDismiss = { showPriceDialog = false }
        )
    }

    Scaffold(
        topBar = {
            Column {
                CustomAppBar(
                    title = stringResource(id = R.string.food_title),
                    alignment = TitleAlignment.CENTER,
                    onBackClick = { navController.popBackStack() },
                    trailingContent = {
                        IconButton(
                            onClick = { navController.navigate(CompanyList(serviceType = "FOOD")) }
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
                        val priceLabel = if (state.draftMinPrice != null || state.draftMaxPrice != null) {
                            "${state.draftMinPrice ?: 0} - ${state.draftMaxPrice ?: "∞"} ₼"
                        } else null
                        FilterChipItem(
                            label = stringResource(id = com.example.baltazar.core.R.string.filter_price_range),
                            selectedValue = priceLabel,
                            isActive = state.draftMinPrice != null || state.draftMaxPrice != null,
                            onClick = { showPriceDialog = true },
                            onClearClick = if (state.draftMinPrice != null || state.draftMaxPrice != null) {
                                { viewModel.setDraftPrice(null, null) }
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
                        icon = TablerIcons.Building,
                        title = "Axtarışa uyğun nəticə tapılmadı",
                        subtitle = "Daxil etdiyiniz filter kriteriyalarına uyğun heç bir təam tapılmadı.",
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
                                FoodCard(
                                    food = null,
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
                                FoodCard(
                                    food = item,
                                    isLoading = false,
                                    cardViewMode = state.cardViewMode,
                                    isFavorite = item.isLiked,
                                    onFavoriteClick = { isFav ->
                                        viewModel.authGateManager.requireAuth(navController) {
                                            viewModel.toggleFavorite(item.id, isFav)
                                        }
                                    },
                                    onClick = { navController.navigate(FoodDetail(item.id)) }
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
