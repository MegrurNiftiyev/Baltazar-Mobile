package com.example.baltazar.feature.company.ui.screens.company_detail

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.pulltorefresh.PullToRefreshBox
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import coil.compose.AsyncImage
import com.example.baltazar.core.R
import com.example.baltazar.core.core.components.CustomAppBar
import com.example.baltazar.core.core.components.ErrorBox
import com.example.baltazar.core.core.constants.BorderRadiuses
import com.example.baltazar.core.core.constants.IconSizes
import com.example.baltazar.core.core.constants.Paddings
import com.example.baltazar.core.core.constants.Spaces
import com.example.baltazar.core.core.extensions.navigateToServiceDetail
import com.example.baltazar.core.core.extensions.setPreviousResult
import com.example.baltazar.core.core.navigation.LikeResult
import com.example.baltazar.core.core.managers.requireAuth
import com.example.baltazar.core.core.navigation.Login
import com.example.baltazar.core.core.navigation.NavResultKeys
import com.example.baltazar.core.core.utils.AppSnackbar
import com.example.baltazar.core.core.utils.SnackbarType
import com.example.baltazar.feature.company.core.enums.CompanySectionType
import com.example.baltazar.feature.company.ui.components.DetailShimmer
import com.example.baltazar.feature.company.ui.components.sections.AboutSectionComponent
import com.example.baltazar.feature.company.ui.components.sections.GallerySection
import com.example.baltazar.feature.company.ui.components.sections.HeaderSection
import com.example.baltazar.feature.company.ui.components.sections.ItemsSection
import com.example.baltazar.feature.company.ui.components.sections.ReviewsSection
import compose.icons.TablerIcons
import compose.icons.tablericons.Building

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CompanyDetailScreen(
    navController: NavHostController,
    companyId: String? = null,
    viewModel: CompanyDetailViewModel = hiltViewModel()
) {
    val state by viewModel.state.collectAsState()
    val context = LocalContext.current

    LaunchedEffect(companyId) {
        if (!companyId.isNullOrBlank()) {
            viewModel.setCompanyId(companyId)
        }
    }

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
        topBar = {
            CustomAppBar(
                title = state.company?.name ?: stringResource(R.string.company_detail),
                onBackClick = { navController.popBackStack() }
            )
        },
        containerColor = MaterialTheme.colorScheme.background
    ) { paddingValues ->
        if (state.isLoading && state.company == null) {
            DetailShimmer(modifier = Modifier.padding(paddingValues))
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
                modifier = Modifier
                    .fillMaxSize()
                    .padding(paddingValues)
            ) {
                if (company != null) {
                    Column(
                        modifier = Modifier
                            .fillMaxSize()
                            .verticalScroll(rememberScrollState())
                            .padding(horizontal = Paddings.LargeMinus, vertical = Paddings.Medium),
                        verticalArrangement = Arrangement.spacedBy(Spaces.Large)
                    ) {
                        // Company profile cover / avatar banner if image available
                        if (!company.coverImageUrl.isNullOrBlank() || !company.logoUrl.isNullOrBlank()) {
                            Box(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .height(160.dp)
                                    .clip(RoundedCornerShape(BorderRadiuses.Large))
                                    .background(MaterialTheme.colorScheme.surfaceContainerHigh)
                            ) {
                                val bannerImage = company.coverImageUrl ?: company.logoUrl
                                AsyncImage(
                                    model = bannerImage,
                                    contentDescription = company.name,
                                    contentScale = ContentScale.Crop,
                                    modifier = Modifier.fillMaxSize()
                                )

                                if (!company.logoUrl.isNullOrBlank() && !company.coverImageUrl.isNullOrBlank()) {
                                    Box(
                                        modifier = Modifier
                                            .size(64.dp)
                                            .align(Alignment.BottomStart)
                                            .padding(start = Paddings.Medium, bottom = Paddings.Medium)
                                            .clip(CircleShape)
                                            .background(MaterialTheme.colorScheme.surface)
                                            .padding(2.dp),
                                        contentAlignment = Alignment.Center
                                    ) {
                                        AsyncImage(
                                            model = company.logoUrl,
                                            contentDescription = company.name,
                                            contentScale = ContentScale.Crop,
                                            modifier = Modifier
                                                .fillMaxSize()
                                                .clip(CircleShape)
                                        )
                                    }
                                }
                            }
                        }

                        // Dynamic section rendering based on company.sectionOrder
                        company.sectionOrder.forEach { sectionType ->
                            when (sectionType) {
                                CompanySectionType.HEADER -> {
                                    HeaderSection(
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
                                    AboutSectionComponent(
                                        aboutText = company.about
                                    )
                                }
                                CompanySectionType.GALLERY -> {
                                    GallerySection(
                                        images = company.images
                                    )
                                }
                                CompanySectionType.ITEMS -> {
                                    ItemsSection(
                                        items = state.relatedItems,
                                        onItemClick = { item ->
                                            navController.navigateToServiceDetail(item.serviceType, item.id)
                                        },
                                        onFavoriteClick = { item, isFav ->
                                            viewModel.authGateManager.requireAuth(navController) {
                                                viewModel.toggleRelatedItemFavorite(item.id, isFav)
                                                navController.setPreviousResult(
                                                    NavResultKeys.LIKE_RESULT,
                                                    LikeResult(
                                                        itemId = item.id,
                                                        isLiked = isFav
                                                    )
                                                )
                                            }
                                        }
                                    )
                                }
                                CompanySectionType.REVIEWS -> {
                                    ReviewsSection(
                                        rating = company.rating,
                                        reviewCount = company.reviewCount,
                                        reviews = state.reviews,
                                        isLoading = state.isReviewsLoading
                                    )
                                }
                                CompanySectionType.UNKNOWN -> {}
                            }
                        }
                    }
                }
            }
        }
    }
}

