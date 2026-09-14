package com.example.baltazar.feature.order.ui.screens.map_delivery_selection

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.baltazar.feature.order.domain.model.LocationSearchResult
import com.example.baltazar.feature.order.domain.repository.ILocationRepository
import com.example.baltazar.feature.order.domain.repository.IOrderRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers.IO
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MapDeliverySelectionViewModel @Inject constructor(
    private val orderRepository: IOrderRepository,
    private val locationRepository: ILocationRepository,
    savedStateHandle: SavedStateHandle
) : ViewModel() {

    private val orderId: String = savedStateHandle["orderId"] ?: ""

    private val _state = MutableStateFlow(MapDeliverySelectionState())
    val state: StateFlow<MapDeliverySelectionState> = _state.asStateFlow()

    private var searchJob: Job? = null

    fun onSearchQueryChanged(query: String) {
        _state.update { it.copy(searchQuery = query) }
        searchJob?.cancel()
        if (query.isBlank()) {
            _state.update { it.copy(searchResults = emptyList(), isSearching = false) }
            return
        }

        searchJob = viewModelScope.launch(IO) {
            delay(400)
            _state.update { it.copy(isSearching = true) }
            locationRepository.searchLocation(query)
                .onSuccess { results ->
                    _state.update { it.copy(searchResults = results, isSearching = false) }
                }
                .onFailure {
                    _state.update { it.copy(isSearching = false) }
                }
        }
    }

    fun selectLocationResult(result: LocationSearchResult) {
        _state.update {
            it.copy(
                selectedLat = result.lat,
                selectedLng = result.lng,
                selectedAddressName = result.addressName,
                searchQuery = "",
                searchResults = emptyList()
            )
        }
    }

    fun onCoordinatesSelected(lat: Double, lng: Double) {
        viewModelScope.launch(IO) {
            _state.update { it.copy(selectedLat = lat, selectedLng = lng) }
            locationRepository.reverseGeocode(lat, lng)
                .onSuccess { result ->
                    _state.update { it.copy(selectedAddressName = result.addressName) }
                }
                .onFailure {
                    _state.update { it.copy(selectedAddressName = "%.5f, %.5f".format(lat, lng)) }
                }
        }
    }

    fun onDeliveryInstructionsChanged(instructions: String) {
        _state.update { it.copy(deliveryInstructions = instructions) }
    }

    fun confirmDeliveryAddress(onSuccessNavigate: (String) -> Unit) {
        if (orderId.isBlank()) {
            onSuccessNavigate("PAYMENT_SCREEN")
            return
        }
        viewModelScope.launch(IO) {
            _state.update { it.copy(isSaving = true, errorMessage = null) }
            orderRepository.patchDeliveryAddress(
                orderId = orderId,
                lat = _state.value.selectedLat,
                lng = _state.value.selectedLng,
                addressName = _state.value.selectedAddressName
            ).onSuccess {
                orderRepository.getNextScreen(orderId)
                    .onSuccess { nextScreenResult ->
                        _state.update { state -> state.copy(isSaving = false) }
                        onSuccessNavigate(nextScreenResult.screen.name)
                    }
                    .onFailure { error ->
                        _state.update { state -> state.copy(isSaving = false, errorMessage = error.message) }
                        onSuccessNavigate("PAYMENT_SCREEN")
                    }
            }.onFailure { error ->
                _state.update { state -> state.copy(isSaving = false, errorMessage = error.message) }
            }
        }
    }
}
