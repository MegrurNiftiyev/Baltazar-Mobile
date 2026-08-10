package com.example.baltazar.feature.auth.ui.screens.onboarding

import androidx.compose.animation.AnimatedContent
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.togetherWith
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.example.baltazar.core.components.CustomTextButton
import com.example.baltazar.core.components.RoundedButton
import com.example.baltazar.core.constants.AppDurations
import com.example.baltazar.core.constants.BorderRadiuses
import com.example.baltazar.core.constants.IconSizes
import com.example.baltazar.core.constants.Paddings
import com.example.baltazar.core.constants.Spaces
import com.example.baltazar.core.enums.CornerShape
import com.example.baltazar.core.navigation.AuthSelection
import com.example.baltazar.feature.auth.R
import com.example.baltazar.feature.auth.core.extensions.dropShadow
import com.example.baltazar.feature.auth.domain.model.OnboardingModel
import compose.icons.TablerIcons
import compose.icons.tablericons.ArrowRight
import kotlinx.coroutines.launch

@Composable
fun OnboardingScreen(
    navController: NavController,
    modifier: Modifier = Modifier,
    viewModel: OnboardingViewModel = hiltViewModel()
) {
    val pages = remember {
        listOf(
            OnboardingModel(
                R.string.onboarding_title_1,
                R.string.onboarding_description_1,
                R.drawable.onboarding_1
            ),
            OnboardingModel(
                R.string.onboarding_title_2,
                R.string.onboarding_description_2,
                R.drawable.onboarding_2
            ),
            OnboardingModel(
                R.string.onboarding_title_3,
                R.string.onboarding_description_3,
                R.drawable.onboarding_2
            )
        )
    }
    val pagerState = rememberPagerState(pageCount = { pages.size })
    val coroutineScope = rememberCoroutineScope()
    val isLastPage = pagerState.currentPage == pages.size - 1

    fun finishOnboarding() {
        viewModel.setOnboardingCompleted()
        navController.navigate(AuthSelection) {
            popUpTo(0) { inclusive = true }
        }
    }

    Column(

        modifier = modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.background),
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = Paddings.Medium)
                .padding(top = Spaces.Large + Spaces.Medium),
            horizontalArrangement = Arrangement.End
        ) {
            if (!isLastPage) {
                CustomTextButton(
                    text = stringResource(R.string.onboarding_skip),
                    onClick = { finishOnboarding() }
                )
            } else {
                Spacer(modifier = Modifier.height(Spaces.Giant))
            }
        }

        HorizontalPager(
            state = pagerState,
            modifier = Modifier
                .weight(1f)
                .fillMaxWidth()
        ) { page ->
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(Paddings.Small),
                contentAlignment = Alignment.Center
            ) {
                Image(
                    painter = painterResource(id = pages[page].imageSource),
                    contentDescription = null,
                    contentScale = ContentScale.Fit,
                    modifier = Modifier
                        .fillMaxWidth(1f)
                        .aspectRatio(1f)
                )
            }
        }

        Box(
            modifier = Modifier
                .fillMaxWidth()
                .dropShadow(
                    shape = RoundedCornerShape(
                        topStart = BorderRadiuses.Huge,
                        topEnd = BorderRadiuses.Huge
                    ),
                    color = MaterialTheme.colorScheme.primary.copy(alpha = 0.05f),
                    blur = BorderRadiuses.Huge,
                    offsetX = 0.dp,
                    offsetY = -Spaces.ExtraSmall,
                    spread = 0.dp
                )
                .clip(RoundedCornerShape(topStart = BorderRadiuses.Huge, topEnd = BorderRadiuses.Huge))
                .background(MaterialTheme.colorScheme.background)
                .heightIn(max = 300.dp)
                .padding(horizontal = Paddings.Medium, vertical = Paddings.Large),
        ) {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .heightIn(min = 320.dp - (Paddings.Large * 2)),
                horizontalAlignment = Alignment.CenterHorizontally,

                ) {


                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(148.dp),
                    contentAlignment = Alignment.Center
                ) {
                    AnimatedContent(
                        targetState = pagerState.currentPage,
                        transitionSpec = {
                            fadeIn(
                                animationSpec = tween(
                                    AppDurations.ExtraShort.inWholeMilliseconds.toInt()
                                )
                            ) togetherWith fadeOut(
                                animationSpec = tween(
                                    AppDurations.SuperShort.inWholeMilliseconds.toInt()
                                )
                            )
                        },
                        label = "OnboardingTextFade"
                    ) { page ->
                        Column(horizontalAlignment = Alignment.CenterHorizontally) {
                            Text(
                                text = stringResource(pages[page].title),
                                style = MaterialTheme.typography.headlineMedium,
                                color = MaterialTheme.colorScheme.onBackground,
                                textAlign = TextAlign.Center,
                                maxLines = 2,
                                overflow = TextOverflow.Ellipsis
                            )

                            Spacer(modifier = Modifier.height(Spaces.Small))

                            Text(
                                text = stringResource(pages[page].description),
                                style = MaterialTheme.typography.bodyLarge,
                                color = MaterialTheme.colorScheme.onSurfaceVariant,
                                maxLines = 3,
                                overflow = TextOverflow.Ellipsis,
                                textAlign = TextAlign.Center,
                                modifier = Modifier.padding(horizontal = Paddings.Small)
                            )
                        }
                    }
                }

                Spacer(modifier = Modifier.height(Spaces.Medium))

                Row(
                    horizontalArrangement = Arrangement.Center,
                    modifier = Modifier.fillMaxWidth()
                ) {
                    repeat(pages.size) { index ->
                        val isSelected = pagerState.currentPage == index
                        Box(
                            modifier = Modifier
                                .padding(horizontal = Paddings.ExtraMini)
                                .size(
                                    width = if (isSelected) Spaces.Large else Spaces.SmallMinus,
                                    height = Spaces.SmallMinus
                                )
                                .clip(CircleShape)
                                .background(
                                    if (isSelected) MaterialTheme.colorScheme.primary
                                    else MaterialTheme.colorScheme.surfaceVariant
                                )
                        )
                    }
                }

                Spacer(modifier = Modifier.weight(1f))

                RoundedButton(
                    text = if (isLastPage) stringResource(R.string.onboarding_start) else stringResource(
                        R.string.onboarding_next
                    ),
                    trailingIcon = if (isLastPage) null else TablerIcons.ArrowRight,
                    trailingIconSize = IconSizes.Medium,
                    shape = CornerShape.Circle,
                    textStyle = MaterialTheme.typography.labelLarge,
                    onClick = {
                        if (!isLastPage) {
                            coroutineScope.launch {
                                pagerState.animateScrollToPage(pagerState.currentPage + 1)
                            }
                        } else {
                            finishOnboarding()
                        }
                    }
                )
            }
        }
    }
}