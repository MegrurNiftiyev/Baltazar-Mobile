package com.example.baltazar.feature.order.ui.screens.map_delivery_selection

import com.example.baltazar.core.core.constants.DefaultLocationConstants
import com.example.baltazar.feature.order.domain.model.LocationSearchResult
import com.example.baltazar.feature.order.domain.model.NextScreenType

data class MapDeliverySelectionState(
    val isLoading: Boolean = false,
    val searchQuery: String = "",
    val searchResults: List<LocationSearchResult> = emptyList(),
    val isSearching: Boolean = false,
    val selectedLat: Double = DefaultLocationConstants.DEFAULT_LAT,
    val selectedLng: Double = DefaultLocationConstants.DEFAULT_LNG,
    val selectedAddressName: String = DefaultLocationConstants.DEFAULT_ADDRESS_NAME,
    val deliveryInstructions: String = "",
    val isSaving: Boolean = false,
    val errorMessage: String? = null,
    val nextScreenType: NextScreenType? = null
)
