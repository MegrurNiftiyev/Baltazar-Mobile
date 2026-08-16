package com.example.baltazar.feature.company.ui.screens.company_list

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
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
import com.example.baltazar.core.R
import com.example.baltazar.core.core.components.CustomAppBar
import com.example.baltazar.core.core.components.ErrorBox
import com.example.baltazar.core.core.constants.Paddings
import com.example.baltazar.core.core.constants.Spaces
import com.example.baltazar.core.core.enums.TitleAlignment
import com.example.baltazar.core.core.navigation.CompanyDetail
import com.example.baltazar.feature.company.ui.components.CardShimmer
import com.example.baltazar.feature.company.ui.components.CompanyCard

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CompanyListScreen(
    navController: NavHostController,
    viewModel: CompanyListViewModel = hiltViewModel()
) {
    val state by viewModel.state.collectAsState()
    val listState = rememberLazyListState()

    val shouldLoadMore by remember {
        derivedStateOf {
            val lastVisibleItem = listState.layoutInfo.visibleItemsInfo.lastOrNull()
            lastVisibleItem != null && lastVisibleItem.index >= listState.layoutInfo.totalItemsCount - 2
        }
    }

    LaunchedEffect(shouldLoadMore) {
        if (shouldLoadMore && !state.isLoading && !state.isLoadingMore && state.hasMore) {
            viewModel.loadMore()
        }
    }

    Scaffold(
        topBar = {
            CustomAppBar(
                title = stringResource(id = R.string.companies_title),
                alignment = TitleAlignment.CENTER,
                onBackClick = { navController.popBackStack() }
            )
        },
        containerColor = MaterialTheme.colorScheme.background
    ) { paddingValues ->
        if (state.error != null && state.companies.isEmpty() && !state.isLoading) {
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(paddingValues)
                    .padding(Paddings.Large),
                contentAlignment = Alignment.Center
            ) {
                ErrorBox(
                    title = state.error ?: stringResource(id = R.string.data_load_failed),
                    onRetry = { viewModel.loadCompanies() }
                )
            }
        } else {
            PullToRefreshBox(
                isRefreshing = state.isRefreshing,
                onRefresh = { viewModel.refresh() },
                modifier = Modifier
                    .fillMaxSize()
                    .padding(paddingValues)
            ) {
                LazyColumn(
                    state = listState,
                    contentPadding = PaddingValues(Paddings.Medium),
                    verticalArrangement = Arrangement.spacedBy(Spaces.Medium),
                    modifier = Modifier.fillMaxSize()
                ) {
                    if (state.isLoading && state.companies.isEmpty()) {
                        items(5) {
                            CardShimmer()
                        }
                    } else {
                        items(
                            items = state.companies,
                            key = { it.id }
                        ) { company ->
                            CompanyCard(
                                name = company.name,
                                logoUrl = company.logoUrl,
                                coverImageUrl = company.coverImageUrl,
                                rating = company.rating,
                                reviewCount = company.reviewCount,
                                category = company.category,
                                address = company.address,
                                onClick = {
                                    navController.navigate(CompanyDetail(id = company.id))
                                }
                            )
                        }

                        if (state.isLoadingMore) {
                            item {
                                Box(
                                    modifier = Modifier
                                        .fillMaxWidth()
                                        .padding(Paddings.Medium),
                                    contentAlignment = Alignment.Center
                                ) {
                                    CircularProgressIndicator(color = MaterialTheme.colorScheme.primary)
                                }
                            }
                        }
                    }
                }
            }
        }
    }
}
