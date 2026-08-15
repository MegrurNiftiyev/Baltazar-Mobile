package com.example.baltazar.feature.hotel.ui.screens.hotel_detail

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
import androidx.compose.ui.text.style.TextOverflow
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.example.baltazar.core.R
import com.example.baltazar.core.core.components.DetailAboutSection
import com.example.baltazar.core.core.components.DetailBottomBar
import com.example.baltazar.core.core.components.DetailTopBarOverlay
import com.example.baltazar.core.core.components.DetailTopImageCarousel
import com.example.baltazar.core.core.components.ErrorBox
import com.example.baltazar.core.core.components.IncludedServicesSection
import com.example.baltazar.core.core.components.ReviewSection
import com.example.baltazar.core.core.components.SectionTitle
import com.example.baltazar.core.core.components.ShimmerWrapper
import com.example.baltazar.core.core.constants.BorderRadiuses
import com.example.baltazar.core.core.constants.IconSizes
import com.example.baltazar.core.core.constants.Paddings
import com.example.baltazar.core.core.constants.Spaces
import com.example.baltazar.core.core.utils.AppSnackbar
import com.example.baltazar.core.core.utils.SnackbarType
import com.example.baltazar.feature.hotel.ui.components.HotelRoomCard
import com.example.baltazar.feature.hotel.ui.components.HotelRoomCardShimmer
import compose.icons.TablerIcons
import compose.icons.tablericons.MapPin
import kotlinx.coroutines.launch

@Composable
fun HotelDetailScreen(
    navController: NavController,
    viewModel: HotelDetailViewModel = hiltViewModel()
) {
    val state by viewModel.state.collectAsState()
    val context = LocalContext.current
    val coroutineScope = rememberCoroutineScope()
    val reservationSoonMsg = stringResource(R.string.reservation_coming_soon)

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
                price = state.effectivePrice,
                currency = "AZN",
                priceSuffix = state.hotel.priceSuffix,
                actionButtonText = stringResource(R.string.reserve),
                isLoading = state.isLoading,
                onActionClick = {
                    coroutineScope.launch {
                        AppSnackbar.success(reservationSoonMsg)
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
                    onRetry = { viewModel.loadHotelDetails() }
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
                        images = state.hotel.images,
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
                        // Title, location and rating
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

                                    Spacer(modifier = Modifier.height(Spaces.Small))

                                    ShimmerWrapper(
                                        isLoading = true,
                                        modifier = Modifier
                                            .width(Spaces.Max * 2 + Spaces.GiantMinus)
                                            .height(Spaces.MediumPlus)
                                            .clip(RoundedCornerShape(BorderRadiuses.ExtraMini))
                                    ) {
                                        Box(
                                            modifier = Modifier
                                                .fillMaxWidth()
                                                .height(Spaces.MediumPlus)
                                                .background(MaterialTheme.colorScheme.surfaceContainerHigh)
                                        )
                                    }
                                } else {
                                    Text(
                                        text = state.hotel.name,
                                        style = MaterialTheme.typography.headlineMedium,
                                        color = MaterialTheme.colorScheme.onSurface
                                    )

                                    Spacer(modifier = Modifier.height(Spaces.Small))

                                    Row(verticalAlignment = Alignment.CenterVertically) {
                                        Icon(
                                            imageVector = TablerIcons.MapPin,
                                            contentDescription = null,
                                            tint = MaterialTheme.colorScheme.primary,
                                            modifier = Modifier.size(IconSizes.Medium)
                                        )
                                        Spacer(modifier = Modifier.width(Spaces.ExtraMini))
                                        Text(
                                            text = if (state.hotel.address.isNotBlank()) "${state.hotel.city}, ${state.hotel.address}" else state.hotel.city,
                                            style = MaterialTheme.typography.bodyLarge,
                                            color = MaterialTheme.colorScheme.onSurfaceVariant,
                                            maxLines = 1,
                                            overflow = TextOverflow.Ellipsis
                                        )
                                    }
                                }
                            }

                            if (!state.isLoading && (state.hotel.rating > 0 || state.hotel.starRating > 0)) {
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
                                        text = if (state.hotel.rating > 0) "%.1f".format(state.hotel.rating) else "${state.hotel.starRating}★",
                                        style = MaterialTheme.typography.labelMedium,
                                        fontWeight = FontWeight.Bold,
                                        color = MaterialTheme.colorScheme.onPrimaryContainer
                                    )
                                }
                            }
                        }

                        if (state.hotel.amenities.isNotEmpty() || state.isLoading) {
                            IncludedServicesSection(
                                services = state.hotel.amenities,
                                title = stringResource(R.string.features_title),
                                isLoading = state.isLoading
                            )
                        }

                        if (state.hotel.about.isNotBlank() || state.isLoading) {
                            DetailAboutSection(
                                title = stringResource(R.string.about_service_title),
                                description = state.hotel.about,
                                isLoading = state.isLoading
                            )
                        }

                        if (state.rooms.isNotEmpty() || state.isRoomsLoading || state.isLoading) {
                            Column(verticalArrangement = Arrangement.spacedBy(Spaces.Small)) {
                                SectionTitle(title = stringResource(R.string.rooms_title))

                                if (state.isRoomsLoading || state.isLoading) {
                                    repeat(2) {
                                        HotelRoomCardShimmer()
                                    }
                                } else {
                                    state.rooms.forEach { room ->
                                        HotelRoomCard(
                                            room = room,
                                            priceSuffix = state.hotel.priceSuffix,
                                            isSelected = state.selectedRoomId == room.id,
                                            onClick = { viewModel.selectRoom(room.id) }
                                        )
                                    }
                                }
                            }
                        }

                        ReviewSection(
                            reviews = state.reviews,
                            rating = state.hotel.rating,
                            reviewCount = state.hotel.reviewCount,
                            reviewEligibility = state.hotel.reviewEligibility,
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
