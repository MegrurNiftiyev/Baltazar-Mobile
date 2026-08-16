package com.example.baltazar.feature.travel.ui.screens.tour_roadmap

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.navigationBars
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.statusBars
import androidx.compose.foundation.layout.windowInsetsPadding
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.example.baltazar.core.core.components.DetailFloatingActionButton
import com.example.baltazar.core.core.constants.Paddings
import com.example.baltazar.feature.travel.ui.screens.tour_roadmap.components.MapErrorFallback
import com.example.baltazar.feature.travel.ui.screens.tour_roadmap.components.RoadmapBottomList
import com.google.android.gms.maps.model.CameraPosition
import com.google.android.gms.maps.model.LatLng
import com.google.maps.android.compose.GoogleMap
import com.google.maps.android.compose.Marker
import com.google.maps.android.compose.MarkerState
import com.google.maps.android.compose.Polyline
import com.google.maps.android.compose.rememberCameraPositionState
import compose.icons.TablerIcons
import compose.icons.tablericons.ArrowLeft

@Composable
fun TourRoadmapScreen(
    navController: NavController,
    viewModel: TourRoadmapViewModel = hiltViewModel()
) {
    val state by viewModel.state.collectAsState()

    val defaultLatLng = remember(state.roadmap) {
        val firstValid = state.roadmap.firstOrNull { it.lat != 0.0 || it.long != 0.0 }
        if (firstValid != null) LatLng(firstValid.lat, firstValid.long)
        else LatLng(40.4093, 49.8671) // Default Baku
    }

    val cameraPositionState = rememberCameraPositionState {
        position = CameraPosition.fromLatLngZoom(defaultLatLng, 10f)
    }

    LaunchedEffect(state.selectedPoint) {
        state.selectedPoint?.let { pt ->
            if (pt.lat != 0.0 || pt.long != 0.0) {
                cameraPositionState.position = CameraPosition.fromLatLngZoom(LatLng(pt.lat, pt.long), 13f)
            }
        }
    }

    Box(modifier = Modifier.fillMaxSize()) {
        val validPoints = state.roadmap.filter { it.lat != 0.0 || it.long != 0.0 }
        val polylinePoints = validPoints.map { LatLng(it.lat, it.long) }

        if (validPoints.isNotEmpty()) {
            GoogleMap(
                modifier = Modifier.fillMaxSize(),
                cameraPositionState = cameraPositionState
            ) {
                // Markers with 1, 2, 3...
                validPoints.forEach { point ->
                    Marker(
                        state = MarkerState(position = LatLng(point.lat, point.long)),
                        title = "${point.order}. ${point.name}",
                        snippet = "Məntəqə ${point.order}"
                    )
                }

                // Polyline connecting points
                if (polylinePoints.size > 1) {
                    Polyline(
                        points = polylinePoints,
                        color = MaterialTheme.colorScheme.primary,
                        width = 10f
                    )
                }
            }

            // Floating Back Button
            DetailFloatingActionButton(
                icon = TablerIcons.ArrowLeft,
                contentDescription = "Back",
                onClick = { navController.popBackStack() },
                modifier = Modifier
                    .align(Alignment.TopStart)
                    .windowInsetsPadding(WindowInsets.statusBars)
                    .padding(Paddings.Medium)
            )

            // Bottom Checkpoint Carousel
            RoadmapBottomList(
                roadmap = state.roadmap,
                selectedPoint = state.selectedPoint,
                onPointClick = { viewModel.selectPoint(it) },
                modifier = Modifier
                    .align(Alignment.BottomCenter)
                    .windowInsetsPadding(WindowInsets.navigationBars)
                    .padding(bottom = Paddings.Medium)
            )
        } else {
            MapErrorFallback(
                modifier = Modifier.fillMaxSize()
            )

            // Floating Back Button over error fallback
            DetailFloatingActionButton(
                icon = TablerIcons.ArrowLeft,
                contentDescription = "Back",
                onClick = { navController.popBackStack() },
                modifier = Modifier
                    .align(Alignment.TopStart)
                    .windowInsetsPadding(WindowInsets.statusBars)
                    .padding(Paddings.Medium)
            )
        }
    }
}
