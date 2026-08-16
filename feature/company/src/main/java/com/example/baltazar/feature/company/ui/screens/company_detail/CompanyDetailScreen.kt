package com.example.baltazar.feature.company.ui.screens.company_detail

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
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
import androidx.compose.ui.draw.clip
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import com.example.baltazar.core.R
import com.example.baltazar.core.core.components.DetailTopBarOverlay
import com.example.baltazar.core.core.components.DetailTopImageCarousel
import com.example.baltazar.core.core.components.ErrorBox
import com.example.baltazar.core.core.constants.BorderRadiuses
import com.example.baltazar.core.core.constants.Paddings
import com.example.baltazar.core.core.constants.Spaces
import com.example.baltazar.core.core.utils.AppSnackbar
import com.example.baltazar.core.core.utils.SnackbarType
import com.example.baltazar.feature.company.domain.model.CompanySectionType
import com.example.baltazar.feature.company.ui.components.CompanyDetailShimmer
import com.example.baltazar.feature.company.ui.components.sections.CompanyAboutSectionComponent
import com.example.baltazar.feature.company.ui.components.sections.CompanyGallerySection
import com.example.baltazar.feature.company.ui.components.sections.CompanyHeaderSection
import com.example.baltazar.feature.company.ui.components.sections.CompanyItemsSection
import com.example.baltazar.feature.company.ui.components.sections.CompanyReviewsSection

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CompanyDetailScreen(
    navController: NavHostController,
    viewModel: CompanyDetailViewModel = hiltViewModel()
) {
    val state by viewModel.state.collectAsState()
    val context = LocalContext.current

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
        contentWindowInsets = WindowInsets(0, 0, 0, 0),
        containerColor = MaterialTheme.colorScheme.background
    ) { paddingValues ->
        if (state.isLoading && state.company == null) {
            CompanyDetailShimmer(modifier = Modifier.padding(paddingValues))
        } else if (state.error != null && state.company == null) {
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(paddingValues)
                    .padding(Paddings.Large),
                contentAlignment = Alignment.Center
            ) {
                ErrorBox(
                    title = state.error ?: stringResource(R.string.data_load_failed),
                    onRetry = { viewModel.loadCompanyDetails() }
                )
            }
        } else {
            val company = state.company
            PullToRefreshBox(
                isRefreshing = state.isLoading,
                onRefresh = { viewModel.loadCompanyDetails() },
                modifier = Modifier.fillMaxSize()
            ) {
                Box(modifier = Modifier.fillMaxSize()) {
                    if (company != null) {
                        Column(
                            modifier = Modifier
                                .fillMaxSize()
                                .verticalScroll(rememberScrollState())
                                .padding(bottom = paddingValues.calculateBottomPadding())
                        ) {
                            DetailTopImageCarousel(
                                images = company.images.ifEmpty {
                                    listOfNotNull(company.coverImageUrl, company.logoUrl)
                                },
                                isLoading = false
                            )

                            // Content card overlapping top carousel
                            Column(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .offset(y = (-32).dp)
                                    .clip(RoundedCornerShape(topStart = BorderRadiuses.Large, topEnd = BorderRadiuses.Large))
                                    .background(MaterialTheme.colorScheme.background)
                                    .padding(horizontal = Paddings.LargeMinus, vertical = Paddings.Large),
                                verticalArrangement = Arrangement.spacedBy(Spaces.Large)
                            ) {
                                // Dynamic section rendering based on company.sectionOrder
                                company.sectionOrder.forEach { sectionType ->
                                    when (sectionType) {
                                        CompanySectionType.HEADER -> {
                                            CompanyHeaderSection(
                                                name = company.name,
                                                category = company.category,
                                                rating = company.rating,
                                                reviewCount = company.reviewCount,
                                                address = company.address,
                                                workingHours = company.workingHours,
                                                phone = company.phone,
                                                email = company.email
                                            )
                                        }
                                        CompanySectionType.ABOUT -> {
                                            CompanyAboutSectionComponent(
                                                about = company.about
                                            )
                                        }
                                        CompanySectionType.GALLERY -> {
                                            CompanyGallerySection(
                                                images = company.images
                                            )
                                        }
                                        CompanySectionType.ITEMS -> {
                                            CompanyItemsSection(
                                                items = state.relatedItems
                                            )
                                        }
                                        CompanySectionType.REVIEWS -> {
                                            CompanyReviewsSection(
                                                rating = company.rating,
                                                reviewCount = company.reviewCount
                                            )
                                        }
                                        CompanySectionType.UNKNOWN -> {}
                                    }
                                }
                            }
                        }
                    }

                    // Top Bar Overlay
                    DetailTopBarOverlay(
                        isFavorite = state.isFavorite,
                        onBackClick = { navController.popBackStack() },
                        onFavoriteClick = { viewModel.toggleFavorite(!state.isFavorite) }
                    )
                }
            }
        }
    }
}
