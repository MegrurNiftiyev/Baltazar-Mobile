package com.example.baltazar.feature.rentacar.ui.screens.car_detail

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Star
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.example.baltazar.core.R
import com.example.baltazar.core.core.components.DetailBottomBar
import com.example.baltazar.core.core.components.DetailTopBarOverlay
import com.example.baltazar.core.core.components.DetailTopImageCarousel
import com.example.baltazar.core.core.components.ErrorBox
import com.example.baltazar.core.core.components.IncludedServicesSection
import com.example.baltazar.core.core.components.ReviewSection
import com.example.baltazar.core.core.components.ShimmerWrapper
import com.example.baltazar.core.core.constants.BorderRadiuses
import com.example.baltazar.core.core.constants.IconSizes
import com.example.baltazar.core.core.constants.Paddings
import com.example.baltazar.core.core.constants.Spaces
import com.example.baltazar.core.core.utils.AppSnackbar
import com.example.baltazar.core.core.utils.SnackbarType
import com.example.baltazar.feature.rentacar.ui.components.CarSpecsGrid
import kotlinx.coroutines.launch

@Composable
fun CarDetailScreen(
    navController: NavController,
    viewModel: CarDetailViewModel = hiltViewModel()
) {
    val state by viewModel.state.collectAsState()
    val context = LocalContext.current
    val coroutineScope = rememberCoroutineScope()
    val rentalSoonMsg = stringResource(R.string.rental_coming_soon)

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
        bottomBar = {
            DetailBottomBar(
                price = state.car.price,
                currency = "AZN",
                priceSuffix = state.car.priceSuffix,
                actionButtonText = stringResource(R.string.rent),
                isLoading = state.isLoading,
                onActionClick = {
                    coroutineScope.launch {
                        AppSnackbar.success(rentalSoonMsg)
                    }
                }
            )
        },
        containerColor = MaterialTheme.colorScheme.background
    ) { paddingValues ->
        if (state.error != null && !state.isLoading) {
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(paddingValues)
                    .padding(Paddings.Large),
                contentAlignment = Alignment.Center
            ) {
                ErrorBox(
                    title = state.error ?: stringResource(R.string.data_load_failed),
                    onRetry = { viewModel.loadCarDetails() }
                )
            }
        } else {
            Box(modifier = Modifier.fillMaxSize()) {
                Column(
                    modifier = Modifier
                        .fillMaxSize()
                        .verticalScroll(rememberScrollState())
                        .padding(bottom = paddingValues.calculateBottomPadding())
                ) {
                    DetailTopImageCarousel(
                        images = state.car.images,
                        isLoading = state.isLoading
                    )

                    // Content card overlapping top image
                    Column(
                        modifier = Modifier
                            .fillMaxWidth()
                            .offset(y = -Spaces.ExtraLarge)
                            .clip(RoundedCornerShape(topStart = BorderRadiuses.ExtraLarge, topEnd = BorderRadiuses.ExtraLarge))
                            .background(MaterialTheme.colorScheme.background)
                            .padding(horizontal = Paddings.LargeMinus, vertical = Paddings.Large),
                        verticalArrangement = Arrangement.spacedBy(Spaces.Large)
                    ) {
                        // Title and rating
                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.SpaceBetween,
                            verticalAlignment = Alignment.Top
                        ) {
                            Column(
                                modifier = Modifier
                                    .weight(1f)
                                    .padding(end = Spaces.Small)
                            ) {
                                if (state.isLoading) {
                                    ShimmerWrapper(
                                        isLoading = true,
                                        modifier = Modifier
                                            .width(Spaces.Max * 3 + Spaces.LargeMinus)
                                            .height(Spaces.ExtraLargePlus)
                                            .clip(RoundedCornerShape(BorderRadiuses.ExtraMini))
                                    ) {
                                        Box(
                                            modifier = Modifier
                                                .fillMaxWidth()
                                                .height(Spaces.ExtraLargePlus)
                                                .background(MaterialTheme.colorScheme.surfaceContainerHigh)
                                        )
                                    }

                                    Spacer(modifier = Modifier.height(Spaces.Small))

                                    ShimmerWrapper(
                                        isLoading = true,
                                        modifier = Modifier
                                            .width(Spaces.Max + Spaces.GiantMinus)
                                            .height(Spaces.Medium)
                                            .clip(RoundedCornerShape(BorderRadiuses.ExtraMini))
                                    ) {
                                        Box(
                                            modifier = Modifier
                                                .fillMaxWidth()
                                                .height(Spaces.Medium)
                                                .background(MaterialTheme.colorScheme.surfaceContainerHigh)
                                        )
                                    }
                                } else {
                                    Text(
                                        text = "${state.car.brand} ${state.car.model}",
                                        style = MaterialTheme.typography.headlineMedium,
                                        color = MaterialTheme.colorScheme.onSurface
                                    )

                                    if (state.car.category.isNotBlank()) {
                                        Spacer(modifier = Modifier.height(Spaces.Small))
                                        Text(
                                            text = state.car.category,
                                            style = MaterialTheme.typography.bodyLarge,
                                            color = MaterialTheme.colorScheme.primary
                                        )
                                    }
                                }
                            }

                            if (!state.isLoading && state.car.rating > 0) {
                                Row(
                                    verticalAlignment = Alignment.CenterVertically,
                                    modifier = Modifier
                                        .clip(RoundedCornerShape(BorderRadiuses.Small))
                                        .background(MaterialTheme.colorScheme.primaryContainer)
                                        .padding(horizontal = Paddings.Small, vertical = Paddings.Mini)
                                ) {
                                    Icon(
                                        imageVector = Icons.Filled.Star,
                                        contentDescription = null,
                                        tint = MaterialTheme.colorScheme.primary,
                                        modifier = Modifier.size(IconSizes.MediumMinus)
                                    )
                                    Spacer(modifier = Modifier.width(Spaces.ExtraMini))
                                    Text(
                                        text = "%.1f".format(state.car.rating),
                                        style = MaterialTheme.typography.labelMedium,
                                        fontWeight = FontWeight.Bold,
                                        color = MaterialTheme.colorScheme.onPrimaryContainer
                                    )
                                }
                            }
                        }

                        CarSpecsGrid(
                            transmission = state.car.transmission,
                            fuelType = state.car.fuelType,
                            seats = state.car.seats,
                            year = state.car.year,
                            isLoading = state.isLoading
                        )

                        if (state.car.features.isNotEmpty() || state.isLoading) {
                            IncludedServicesSection(
                                services = state.car.features,
                                title = stringResource(R.string.features_title),
                                isLoading = state.isLoading
                            )
                        }

                        ReviewSection(
                            reviews = state.reviews,
                            rating = state.car.rating,
                            reviewCount = state.car.reviewCount,
                            reviewEligibility = state.car.reviewEligibility,
                            isLoading = state.isReviewsLoading || state.isLoading,
                            isSubmittingReview = state.isSubmittingReview,
                            onSubmitReview = { rating, comment ->
                                viewModel.submitReview(rating, comment)
                            }
                        )
                    }
                }

                // Floating actions overlay
                DetailTopBarOverlay(
                    onBackClick = { navController.popBackStack() },
                    isFavorite = state.isFavorite,
                    onFavoriteClick = { viewModel.toggleFavorite(it) }
                )
            }
        }
    }
}
