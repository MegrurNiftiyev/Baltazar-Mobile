package com.example.baltazar.feature.rentacar.ui.screens.rentacars

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
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
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import com.example.baltazar.core.core.components.CustomAppBar
import com.example.baltazar.core.core.constants.IconSizes
import com.example.baltazar.core.core.constants.Paddings
import com.example.baltazar.core.core.constants.Spaces
import com.example.baltazar.core.core.enums.CardViewMode
import com.example.baltazar.core.core.enums.TitleAlignment
import com.example.baltazar.core.core.navigation.CompanyList
import com.example.baltazar.core.core.navigation.RentACarDetail
import com.example.baltazar.feature.rentacar.R
import com.example.baltazar.feature.rentacar.ui.components.CarCard
import compose.icons.TablerIcons
import compose.icons.tablericons.Building

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun RentACarsScreen(
    navController: NavHostController,
    viewModel: RentACarsViewModel = hiltViewModel()
) {
    val state by viewModel.state.collectAsState()
    val listState = rememberLazyGridState()

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

    Scaffold(
        topBar = {
            CustomAppBar(
                title = stringResource(id = R.string.rentacar_title),
                alignment = TitleAlignment.CENTER,
                onBackClick = { navController.popBackStack() },
                trailingContent = {
                    IconButton(
                        onClick = { navController.navigate(CompanyList(serviceType = "RENT_A_CAR")) }
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
        },
        containerColor = MaterialTheme.colorScheme.background
    ) { paddingValues ->
        val columnCount = if (state.cardViewMode == CardViewMode.GRID) 2 else 1

        PullToRefreshBox(
            isRefreshing = state.isLoading && state.items.isNotEmpty(),
            onRefresh = { viewModel.loadInitialData() },
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
        ) {
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
                        CarCard(
                            car = null,
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
                        CarCard(
                            car = item,
                            isLoading = false,
                            cardViewMode = state.cardViewMode,
                            onClick = { navController.navigate(RentACarDetail(item.id)) }
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
}
