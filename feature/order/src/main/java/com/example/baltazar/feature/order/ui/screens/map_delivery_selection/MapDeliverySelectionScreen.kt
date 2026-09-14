package com.example.baltazar.feature.order.ui.screens.map_delivery_selection

import android.Manifest
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.Clear
import androidx.compose.material.icons.filled.LocationOn
import androidx.compose.material.icons.filled.MyLocation
import androidx.compose.material.icons.filled.Search
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import com.example.baltazar.core.R
import com.example.baltazar.core.core.constants.BorderRadiuses
import com.example.baltazar.core.core.constants.DefaultLocationConstants
import com.example.baltazar.core.core.constants.IconSizes
import com.example.baltazar.core.core.constants.Paddings
import com.example.baltazar.core.core.constants.Spaces
import com.example.baltazar.core.core.managers.PermissionHandlerManager
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.model.CameraPosition
import com.google.android.gms.maps.model.LatLng
import com.google.maps.android.compose.GoogleMap
import com.google.maps.android.compose.MapUiSettings
import com.google.maps.android.compose.Marker
import com.google.maps.android.compose.MarkerState
import com.google.maps.android.compose.rememberCameraPositionState

import androidx.activity.compose.BackHandler
import com.example.baltazar.core.core.components.CustomAlertDialog
import com.example.baltazar.core.core.navigation.Home

import com.google.android.gms.location.LocationServices
import com.google.maps.android.compose.MapProperties

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

    if (showCancelDialog) {
        CustomAlertDialog(
            title = stringResource(id = R.string.cancel_order_dialog_title),
            subtitle = stringResource(id = R.string.cancel_order_dialog_msg),
            confirmText = stringResource(id = R.string.yes_cancel),
            cancelText = stringResource(id = R.string.no_stay),
            isDestructive = true,
            onConfirm = {
                showCancelDialog = false
                navController.navigate(Home()) {
                    popUpTo(0) { inclusive = true }
                }
            },
            onCancel = { showCancelDialog = false }
        )
    }

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

    if (showPermissionDialog) {
        AlertDialog(
            onDismissRequest = { showPermissionDialog = false },
            title = { Text(text = stringResource(id = R.string.location_permission_required)) },
            text = { Text(text = stringResource(id = R.string.permission_denied_settings_msg)) },
            confirmButton = {
                TextButton(
                    onClick = {
                        showPermissionDialog = false
                        permissionManager.openAppSettings(context)
                    }
                ) {
                    Text(text = stringResource(id = R.string.open_settings))
                }
            },
            dismissButton = {
                TextButton(onClick = { showPermissionDialog = false }) {
                    Text(text = stringResource(id = R.string.cancel))
                }
            }
        )
    }

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
        // 1. Full screen interactive Google Map
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

        // 2. Top Search Floating Row
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .statusBarsPadding()
                .padding(horizontal = Paddings.Medium, vertical = Paddings.Small)
                .align(Alignment.TopCenter)
        ) {
            Row(
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier.fillMaxWidth()
            ) {
                // Separate Circular Back Button Box
                Surface(
                    shape = CircleShape,
                    color = MaterialTheme.colorScheme.surface,
                    shadowElevation = Spaces.Small,
                    tonalElevation = Spaces.Small,
                    modifier = Modifier.size(52.dp)
                ) {
                    Box(
                        modifier = Modifier
                            .fillMaxSize()
                            .clickable { showCancelDialog = true },
                        contentAlignment = Alignment.Center
                    ) {
                        Icon(
                            imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                            contentDescription = null,
                            tint = MaterialTheme.colorScheme.onSurface,
                            modifier = Modifier.size(IconSizes.Medium)
                        )
                    }
                }

                Spacer(modifier = Modifier.width(Spaces.Small))

                // Search Bar Surface
                Surface(
                    shape = CircleShape,
                    color = MaterialTheme.colorScheme.surface,
                    shadowElevation = Spaces.Small,
                    tonalElevation = Spaces.Small,
                    modifier = Modifier
                        .weight(1f)
                        .height(52.dp)
                ) {
                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                        modifier = Modifier
                            .fillMaxSize()
                            .padding(horizontal = Paddings.Medium)
                    ) {
                        Icon(
                            imageVector = Icons.Default.Search,
                            contentDescription = null,
                            tint = MaterialTheme.colorScheme.onSurfaceVariant,
                            modifier = Modifier.size(IconSizes.Medium)
                        )

                        Spacer(modifier = Modifier.width(Spaces.Small))

                        BasicTextField(
                            value = state.searchQuery,
                            onValueChange = { viewModel.onSearchQueryChanged(it) },
                            singleLine = true,
                            textStyle = MaterialTheme.typography.bodyLarge.copy(color = MaterialTheme.colorScheme.onSurface),
                            decorationBox = { innerTextField ->
                                if (state.searchQuery.isEmpty()) {
                                    Text(
                                        text = stringResource(id = R.string.search_for_new_address),
                                        style = MaterialTheme.typography.bodyMedium,
                                        color = MaterialTheme.colorScheme.onSurfaceVariant
                                    )
                                }
                                innerTextField()
                            },
                            modifier = Modifier.weight(1f)
                        )

                        if (state.searchQuery.isNotEmpty()) {
                            IconButton(
                                onClick = { viewModel.onSearchQueryChanged("") },
                                modifier = Modifier.size(IconSizes.Large)
                            ) {
                                Icon(
                                    imageVector = Icons.Default.Clear,
                                    contentDescription = null,
                                    tint = MaterialTheme.colorScheme.onSurfaceVariant,
                                    modifier = Modifier.size(IconSizes.Medium)
                                )
                            }
                        }
                    }
                }
            }

            if (state.searchResults.isNotEmpty()) {
                Surface(
                    shape = RoundedCornerShape(BorderRadiuses.Large),
                    color = MaterialTheme.colorScheme.surface,
                    shadowElevation = Spaces.Medium,
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(top = Paddings.Small)
                ) {
                    LazyColumn(
                        modifier = Modifier
                            .fillMaxWidth()
                            .heightIn(max = Paddings.Max * 5)
                    ) {
                        items(state.searchResults) { result ->
                            Row(
                                verticalAlignment = Alignment.CenterVertically,
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .clickable { viewModel.selectLocationResult(result) }
                                    .padding(Paddings.Medium)
                            ) {
                                Icon(
                                    imageVector = Icons.Default.LocationOn,
                                    contentDescription = null,
                                    tint = MaterialTheme.colorScheme.primary,
                                    modifier = Modifier.size(IconSizes.Medium)
                                )
                                Spacer(modifier = Modifier.width(Spaces.Small))
                                Text(
                                    text = result.addressName,
                                    style = MaterialTheme.typography.bodyMedium,
                                    color = MaterialTheme.colorScheme.onSurface
                                )
                            }
                        }
                    }
                }
            }
        }

        // 3. Floating My Location FAB (above bottom sheet)
        FloatingActionButton(
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
            containerColor = MaterialTheme.colorScheme.surface,
            contentColor = MaterialTheme.colorScheme.primary,
            shape = CircleShape,
            modifier = Modifier
                .align(Alignment.BottomEnd)
                .padding(bottom = 220.dp, end = Paddings.Medium)
        ) {
            Icon(
                imageVector = Icons.Default.MyLocation,
                contentDescription = null,
                modifier = Modifier.size(IconSizes.Medium)
            )
        }

        // 4. Bottom Location Details Card
        Surface(
            shape = RoundedCornerShape(topStart = BorderRadiuses.ExtraLarge, topEnd = BorderRadiuses.ExtraLarge),
            color = MaterialTheme.colorScheme.surface,
            shadowElevation = Spaces.Large,
            modifier = Modifier
                .fillMaxWidth()
                .align(Alignment.BottomCenter)
        ) {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .navigationBarsPadding()
                    .padding(Paddings.Medium)
            ) {
                // Drag handle
                Box(
                    modifier = Modifier
                        .width(Spaces.Massive)
                        .height(BorderRadiuses.ExtraMini)
                        .clip(RoundedCornerShape(BorderRadiuses.Small))
                        .background(MaterialTheme.colorScheme.outlineVariant)
                        .align(Alignment.CenterHorizontally)
                )

                Spacer(modifier = Modifier.height(Spaces.Medium))

                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Box(
                        modifier = Modifier
                            .size(Paddings.Max)
                            .clip(CircleShape)
                            .background(MaterialTheme.colorScheme.primary.copy(alpha = 0.12f)),
                        contentAlignment = Alignment.Center
                    ) {
                        Icon(
                            imageVector = Icons.Default.LocationOn,
                            contentDescription = null,
                            tint = MaterialTheme.colorScheme.primary,
                            modifier = Modifier.size(IconSizes.Medium)
                        )
                    }

                    Spacer(modifier = Modifier.width(Spaces.Medium))

                    Column(modifier = Modifier.weight(1f)) {
                        Text(
                            text = state.selectedAddressName.ifBlank { stringResource(id = R.string.delivery_address) },
                            style = MaterialTheme.typography.titleMedium,
                            maxLines = 2,
                            overflow = TextOverflow.Ellipsis,
                            color = MaterialTheme.colorScheme.onSurface
                        )
                        Spacer(modifier = Modifier.height(Spaces.ExtraSmall))
                        Text(
                            text = if (state.deliveryInstructions.isNotBlank()) state.deliveryInstructions else "Bakı, Azərbaycan",
                            style = MaterialTheme.typography.bodyMedium,
                            maxLines = 1,
                            overflow = TextOverflow.Ellipsis,
                            color = MaterialTheme.colorScheme.onSurfaceVariant
                        )
                    }
                }

                Spacer(modifier = Modifier.height(Spaces.Large))

                Button(
                    onClick = { viewModel.confirmDeliveryAddress(onNextScreen) },
                    enabled = !state.isSaving && state.selectedAddressName.isNotBlank(),
                    shape = RoundedCornerShape(BorderRadiuses.Huge),
                    colors = ButtonDefaults.buttonColors(
                        containerColor = MaterialTheme.colorScheme.primary,
                        contentColor = MaterialTheme.colorScheme.onPrimary
                    ),
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(Paddings.ColossalMinus)
                ) {
                    if (state.isSaving) {
                        CircularProgressIndicator(
                            modifier = Modifier.size(IconSizes.Medium),
                            color = MaterialTheme.colorScheme.onPrimary,
                            strokeWidth = BorderRadiuses.ExtraMini
                        )
                    } else {
                        Text(
                            text = stringResource(id = R.string.confirm_address),
                            style = MaterialTheme.typography.titleMedium
                        )
                    }
                }
            }
        }
    }
}
