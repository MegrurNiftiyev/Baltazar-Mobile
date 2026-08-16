package com.example.baltazar.feature.travel.ui.screens.travel_detail

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ExperimentalLayoutApi
import androidx.compose.foundation.layout.FlowRow
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
import com.example.baltazar.core.core.navigation.TourRoadmap
import com.example.baltazar.core.core.utils.AppSnackbar
import com.example.baltazar.core.core.utils.SnackbarType
import com.example.baltazar.feature.travel.ui.components.TourRoadmapTimeline
import compose.icons.TablerIcons
import compose.icons.tablericons.Calendar
import compose.icons.tablericons.Clock
import kotlinx.coroutines.launch

@OptIn(ExperimentalLayoutApi::class)
@Composable
fun TravelDetailScreen(
    navController: NavController,
    viewModel: TravelDetailViewModel = hiltViewModel()
) {
    val state by viewModel.state.collectAsState()
    val context = LocalContext.current
    val coroutineScope = rememberCoroutineScope()
    val tourSoonMsg = stringResource(R.string.tour_coming_soon)

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

    val resolvedServices = if (state.tour.includedServices.isNotEmpty()) {
        state.tour.includedServices.mapNotNull { serviceId ->
            val matched = state.includedServices.firstOrNull { it.id == serviceId }
            if (matched != null) {
                matched.name
            } else if (serviceId.length < 20) {
                serviceId
            } else {
                null
            }
        }
    } else emptyList()

    Scaffold(
        bottomBar = {
            DetailBottomBar(
                price = state.tour.price,
                currency = "AZN",
                priceSuffix = state.tour.priceSuffix,
                actionButtonText = stringResource(R.string.join_tour),
                isLoading = state.isLoading,
                onActionClick = {
                    coroutineScope.launch {
                        AppSnackbar.success(tourSoonMsg)
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
                    onRetry = { viewModel.loadTourDetails() }
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
                        images = state.tour.images,
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
                                            .width(Spaces.Max * 3 + Spaces.GiantMinus)
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
                                } else {
                                    Text(
                                        text = state.tour.title,
                                        style = MaterialTheme.typography.headlineMedium,
                                        color = MaterialTheme.colorScheme.onSurface
                                    )

                                    if (state.tour.categories.isNotEmpty()) {
                                        Spacer(modifier = Modifier.height(Spaces.Small))
                                        FlowRow(
                                            horizontalArrangement = Arrangement.spacedBy(Spaces.ExtraMini),
                                            verticalArrangement = Arrangement.spacedBy(Spaces.ExtraMini)
                                        ) {
                                            state.tour.categories.forEach { category ->
                                                Text(
                                                    text = category,
                                                    style = MaterialTheme.typography.titleSmall,
                                                    fontWeight = FontWeight.SemiBold,
                                                    color = MaterialTheme.colorScheme.primary,
                                                    modifier = Modifier
                                                        .clip(RoundedCornerShape(BorderRadiuses.Small))
                                                        .background(MaterialTheme.colorScheme.primaryContainer.copy(alpha = 0.3f))
                                                        .padding(horizontal = Paddings.Small, vertical = Paddings.ExtraMini)
                                                )
                                            }
                                        }
                                    }
                                }
                            }

                            if (!state.isLoading && state.tour.rating > 0) {
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
                                        text = "%.1f".format(state.tour.rating),
                                        style = MaterialTheme.typography.labelMedium,
                                        fontWeight = FontWeight.Bold,
                                        color = MaterialTheme.colorScheme.onPrimaryContainer
                                    )
                                }
                            }
                        }

                        // Duration & Start Date
                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.spacedBy(Spaces.Small)
                        ) {
                            if (state.tour.duration.isNotBlank() || state.isLoading) {
                                Row(
                                    verticalAlignment = Alignment.CenterVertically,
                                    modifier = Modifier
                                        .weight(1f)
                                        .clip(RoundedCornerShape(BorderRadiuses.Medium))
                                        .background(MaterialTheme.colorScheme.surfaceContainerLow)
                                        .padding(Paddings.Medium)
                                ) {
                                    Icon(
                                        imageVector = TablerIcons.Clock,
                                        contentDescription = null,
                                        tint = MaterialTheme.colorScheme.primary,
                                        modifier = Modifier.size(IconSizes.LargeMinus)
                                    )
                                    Spacer(modifier = Modifier.width(Spaces.Small))
                                    Column {
                                        Text(
                                            text = stringResource(R.string.duration_label),
                                            style = MaterialTheme.typography.labelSmall,
                                            color = MaterialTheme.colorScheme.onSurfaceVariant
                                        )
                                        Text(
                                            text = state.tour.duration.ifBlank { "-" },
                                            style = MaterialTheme.typography.bodyLarge,
                                            color = MaterialTheme.colorScheme.onSurface
                                        )
                                    }
                                }
                            }

                            if (state.tour.startDate.isNotBlank() || state.isLoading) {
                                Row(
                                    verticalAlignment = Alignment.CenterVertically,
                                    modifier = Modifier
                                        .weight(1f)
                                        .clip(RoundedCornerShape(BorderRadiuses.Medium))
                                        .background(MaterialTheme.colorScheme.surfaceContainerLow)
                                        .padding(Paddings.Medium)
                                ) {
                                    Icon(
                                        imageVector = TablerIcons.Calendar,
                                        contentDescription = null,
                                        tint = MaterialTheme.colorScheme.primary,
                                        modifier = Modifier.size(IconSizes.LargeMinus)
                                    )
                                    Spacer(modifier = Modifier.width(Spaces.Small))
                                    Column {
                                        Text(
                                            text = stringResource(R.string.date_label),
                                            style = MaterialTheme.typography.labelSmall,
                                            color = MaterialTheme.colorScheme.onSurfaceVariant
                                        )
                                        Text(
                                            text = state.tour.startDate.ifBlank { "-" },
                                            style = MaterialTheme.typography.bodyLarge,
                                            color = MaterialTheme.colorScheme.onSurface
                                        )
                                    }
                                }
                            }
                        }

                        if (resolvedServices.isNotEmpty() || state.isLoading) {
                            IncludedServicesSection(
                                services = resolvedServices,
                                title = stringResource(R.string.included_title),
                                isLoading = state.isLoading
                            )
                        }

                        if (state.tour.roadmap.isNotEmpty() || state.isLoading) {
                            TourRoadmapTimeline(
                                roadmap = state.tour.roadmap,
                                isLoading = state.isLoading,
                                onMapClick = { navController.navigate(TourRoadmap(state.tour.id)) }
                            )
                        }

                        ReviewSection(
                            reviews = state.reviews,
                            rating = state.tour.rating,
                            reviewCount = state.tour.reviewCount,
                            reviewEligibility = state.tour.reviewEligibility,
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
