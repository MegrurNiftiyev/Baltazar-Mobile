package com.example.baltazar.feature.travel.ui.components

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.example.baltazar.core.R
import com.example.baltazar.core.core.components.SectionTitle
import com.example.baltazar.core.core.constants.BorderRadiuses
import com.example.baltazar.core.core.constants.Spaces
import com.example.baltazar.feature.travel.domain.model.TourRoadmapPoint
import com.example.baltazar.feature.travel.ui.screens.tour_roadmap.components.MapErrorFallback
import com.google.android.gms.maps.model.CameraPosition
import com.google.android.gms.maps.model.LatLng
import com.google.maps.android.compose.GoogleMap
import com.google.maps.android.compose.MapUiSettings
import com.google.maps.android.compose.Marker
import com.google.maps.android.compose.MarkerState
import com.google.maps.android.compose.Polyline
import com.google.maps.android.compose.rememberCameraPositionState

@Composable
fun TourRoadmapTimeline(
    roadmap: List<TourRoadmapPoint>,
    isLoading: Boolean = false,
    onMapClick: (() -> Unit)? = null,
    modifier: Modifier = Modifier
) {
    if (roadmap.isEmpty() && !isLoading) return

    val validPoints = remember(roadmap) {
        roadmap.filter { it.lat != 0.0 || it.long != 0.0 }
    }

    Column(modifier = modifier.fillMaxWidth()) {
        SectionTitle(
            title = stringResource(R.string.tour_roadmap_title),
            actionText = if (validPoints.isNotEmpty() && onMapClick != null) stringResource(R.string.view_on_map) else null,
            onActionClick = if (validPoints.isNotEmpty()) onMapClick else null
        )

        Spacer(modifier = Modifier.height(Spaces.Small))

        Box(
            modifier = Modifier
                .fillMaxWidth()
                .height(180.dp)
                .clip(RoundedCornerShape(BorderRadiuses.Medium))
                .then(
                    if (validPoints.isNotEmpty() && onMapClick != null) {
                        Modifier.clickable(onClick = onMapClick)
                    } else Modifier
                )
        ) {
            if (validPoints.isNotEmpty()) {
                val firstValid = validPoints.first()
                val cameraPositionState = rememberCameraPositionState {
                    position = CameraPosition.fromLatLngZoom(LatLng(firstValid.lat, firstValid.long), 10f)
                }

                GoogleMap(
                    modifier = Modifier.fillMaxSize(),
                    cameraPositionState = cameraPositionState,
                    uiSettings = MapUiSettings(
                        scrollGesturesEnabled = false,
                        zoomGesturesEnabled = false,
                        tiltGesturesEnabled = false,
                        rotationGesturesEnabled = false,
                        mapToolbarEnabled = false,
                        zoomControlsEnabled = false,
                        myLocationButtonEnabled = false
                    )
                ) {
                    validPoints.forEach { point ->
                        Marker(
                            state = MarkerState(position = LatLng(point.lat, point.long)),
                            title = "${point.order}. ${point.name}"
                        )
                    }

                    if (validPoints.size > 1) {
                        Polyline(
                            points = validPoints.map { LatLng(it.lat, it.long) },
                            color = MaterialTheme.colorScheme.primary,
                            width = 8f
                        )
                    }
                }
            } else {
                MapErrorFallback(modifier = Modifier.fillMaxSize())
            }
        }
    }
}
