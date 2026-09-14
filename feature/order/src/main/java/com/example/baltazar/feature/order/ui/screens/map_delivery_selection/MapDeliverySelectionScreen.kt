package com.example.baltazar.feature.order.ui.screens.map_delivery_selection

import android.Manifest
import androidx.activity.compose.BackHandler
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import com.example.baltazar.core.core.constants.DefaultLocationConstants
import com.example.baltazar.core.core.managers.PermissionHandlerManager
import com.example.baltazar.core.core.navigation.Home
import com.example.baltazar.feature.order.ui.screens.map_delivery_selection.components.CancelOrderDialog
import com.example.baltazar.feature.order.ui.screens.map_delivery_selection.components.DeliveryLocationCard
import com.example.baltazar.feature.order.ui.screens.map_delivery_selection.components.LocationPermissionDialog
import com.example.baltazar.feature.order.ui.screens.map_delivery_selection.components.LocationSearchBar
import com.example.baltazar.feature.order.ui.screens.map_delivery_selection.components.MyLocationButton
import com.google.android.gms.location.LocationServices
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.model.CameraPosition
import com.google.android.gms.maps.model.LatLng
import com.google.maps.android.compose.GoogleMap
import com.google.maps.android.compose.MapProperties
import com.google.maps.android.compose.MapUiSettings
import com.google.maps.android.compose.Marker
import com.google.maps.android.compose.MarkerState
import com.google.maps.android.compose.rememberCameraPositionState

@Composable
fun MapDeliverySelectionScreen(
    navController: NavHostController,
    onNextScreen: (String) -> Unit = {},
    viewModel: MapDeliverySelectionViewModel = hiltViewModel()
) {
    val state by viewModel.state.collectAsState()
    val context = LocalContext.current
    val permissionManager = remember { PermissionHandlerManager() }
    val fusedLocationClient = remember { LocationServices.getFusedLocationProviderClient(context) }
    var showPermissionDialog by remember { mutableStateOf(false) }
    var showCancelDialog by remember { mutableStateOf(false) }

    val fetchUserLocation: () -> Unit = {
        if (permissionManager.hasLocationPermission(context)) {
            try {
                fusedLocationClient.lastLocation.addOnSuccessListener { location ->
                    if (location != null) {
                        viewModel.onCoordinatesSelected(location.latitude, location.longitude)
                    } else {
                        viewModel.onCoordinatesSelected(
                            DefaultLocationConstants.DEFAULT_LAT,
                            DefaultLocationConstants.DEFAULT_LNG
                        )
                    }
                }.addOnFailureListener {
                    viewModel.onCoordinatesSelected(
                        DefaultLocationConstants.DEFAULT_LAT,
                        DefaultLocationConstants.DEFAULT_LNG
                    )
                }
            } catch (e: SecurityException) {
                viewModel.onCoordinatesSelected(
                    DefaultLocationConstants.DEFAULT_LAT,
                    DefaultLocationConstants.DEFAULT_LNG
                )
            }
        }
    }

    LaunchedEffect(Unit) {
        if (permissionManager.hasLocationPermission(context)) {
            fetchUserLocation()
        }
    }

    BackHandler {
        showCancelDialog = true
    }

    CancelOrderDialog(
        visible = showCancelDialog,
        onConfirm = {
            showCancelDialog = false
            navController.navigate(Home()) {
                popUpTo(0) { inclusive = true }
            }
        },
        onDismiss = { showCancelDialog = false }
    )

    val locationPermissionLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.RequestMultiplePermissions()
    ) { permissions ->
        val fineGranted = permissions[Manifest.permission.ACCESS_FINE_LOCATION] ?: false
        val coarseGranted = permissions[Manifest.permission.ACCESS_COARSE_LOCATION] ?: false

        if (fineGranted || coarseGranted) {
            fetchUserLocation()
        } else {
            showPermissionDialog = true
        }
    }

    LocationPermissionDialog(
        visible = showPermissionDialog,
        onOpenSettings = {
            showPermissionDialog = false
            permissionManager.openAppSettings(context)
        },
        onDismiss = { showPermissionDialog = false }
    )

    val cameraPositionState = rememberCameraPositionState {
        position = CameraPosition.fromLatLngZoom(
            LatLng(state.selectedLat, state.selectedLng),
            15f
        )
    }

    LaunchedEffect(state.selectedLat, state.selectedLng) {
        val target = LatLng(state.selectedLat, state.selectedLng)
        if (cameraPositionState.position.target != target) {
            cameraPositionState.animate(
                CameraUpdateFactory.newLatLngZoom(target, 15f)
            )
        }
    }

    Box(modifier = Modifier.fillMaxSize()) {
        GoogleMap(
            modifier = Modifier.fillMaxSize(),
            cameraPositionState = cameraPositionState,
            properties = MapProperties(
                isMyLocationEnabled = permissionManager.hasLocationPermission(context)
            ),
            onMapClick = { latLng ->
                viewModel.onCoordinatesSelected(latLng.latitude, latLng.longitude)
            },
            uiSettings = MapUiSettings(
                zoomControlsEnabled = false,
                myLocationButtonEnabled = false
            )
        ) {
            Marker(
                state = MarkerState(position = LatLng(state.selectedLat, state.selectedLng)),
                title = state.selectedAddressName
            )
        }

        LocationSearchBar(
            searchQuery = state.searchQuery,
            onSearchQueryChanged = viewModel::onSearchQueryChanged,
            onBackClick = { showCancelDialog = true },
            searchResults = state.searchResults,
            onResultSelected = viewModel::selectLocationResult,
            modifier = Modifier.align(Alignment.TopCenter)
        )

        MyLocationButton(
            onClick = {
                if (permissionManager.hasLocationPermission(context)) {
                    fetchUserLocation()
                } else {
                    locationPermissionLauncher.launch(
                        arrayOf(
                            Manifest.permission.ACCESS_FINE_LOCATION,
                            Manifest.permission.ACCESS_COARSE_LOCATION
                        )
                    )
                }
            },
            modifier = Modifier.align(Alignment.BottomEnd)
        )

        DeliveryLocationCard(
            addressName = state.selectedAddressName,
            deliveryInstructions = state.deliveryInstructions,
            isSaving = state.isSaving,
            onConfirm = { viewModel.confirmDeliveryAddress(onNextScreen) },
            modifier = Modifier.align(Alignment.BottomCenter)
        )
    }
}
