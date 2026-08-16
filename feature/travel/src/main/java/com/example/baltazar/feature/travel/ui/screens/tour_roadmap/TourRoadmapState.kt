package com.example.baltazar.feature.travel.ui.screens.tour_roadmap

import com.example.baltazar.core.core.utils.SnackbarMessage
import com.example.baltazar.feature.travel.domain.model.TourDetail
import com.example.baltazar.feature.travel.domain.model.TourRoadmapPoint

data class TourRoadmapState(
    val isLoading: Boolean = false,
    val tour: TourDetail = TourDetail(),
    val roadmap: List<TourRoadmapPoint> = emptyList(),
    val selectedPoint: TourRoadmapPoint? = null,
    val error: String? = null,
    val userMessage: SnackbarMessage? = null
)
