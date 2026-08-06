package com.example.baltazar.feature.auth.ui.screens.onboarding

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.stringResource
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.example.baltazar.core.components.RoundedButton
import com.example.baltazar.core.constants.Paddings
import com.example.baltazar.core.constants.Spaces
import com.example.baltazar.core.navigation.AuthSelection
import com.example.baltazar.feature.auth.R
import com.example.baltazar.feature.auth.domain.model.OnboardingModel
import com.example.baltazar.feature.auth.ui.screens.onboarding.components.OnboardingPageItem
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
                R.drawable.onboarding_1
            ),
            OnboardingModel(
                R.string.onboarding_title_3,
                R.string.onboarding_description_3,
                R.drawable.onboarding_1
            )
        )
    }
    val state = viewModel.state.collectAsState().value
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
            .padding(Paddings.Medium)
    ) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.End
        ) {
            if (!isLastPage) {
                TextButton(onClick = { finishOnboarding() }) {
                    Text(
                        text = stringResource(R.string.onboarding_skip),
                        style = MaterialTheme.typography.labelLarge,
                        color = MaterialTheme.colorScheme.onSurfaceVariant
                    )
                }
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
            OnboardingPageItem(
                title = pages[page].title,
                description = pages[page].description,
                imageSource = pages[page].imageSource
            )
        }

        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(vertical = Paddings.Medium),
            horizontalArrangement = Arrangement.Center
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

        Spacer(modifier = Modifier.height(Spaces.Medium))

        RoundedButton(
            text = if (isLastPage) stringResource(R.string.onboarding_start) else stringResource(R.string.onboarding_next),
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

